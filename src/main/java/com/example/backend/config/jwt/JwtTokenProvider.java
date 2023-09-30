package com.example.backend.config.jwt;

import com.example.backend.common.error.BusinessLogicException;
import com.example.backend.common.error.JwtExceptionCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.lettuce.core.RedisException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtTokenProvider {
  public static final String ACCESS_TOKEN_NAME = "accessToken";
  public static final String REFRESH_TOKEN_NAME = "refreshToken";
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${spring.jwt.secret.key}")
  private String secretKey;
  @Value("${spring.jwt.token.access-expiration-time}")
  private long accessExpirationTime;
  @Value("${spring.jwt.token.refresh-expiration-time}")
  private long refreshExpirationTime;
  @Value("${useHttps}")
  private boolean useHttps;

  private final PrincipalDetailsService userDetailsService;
  private final ObjectMapper objectMapper;

  public String createAccessToken(Authentication authentication, HttpServletRequest request)  {
    Claims claims = generateAccessClaims(authentication);
    String accessToken = generateJwtToken(claims,accessExpirationTime);
    String userInfoJson = generateUserInfoJson(authentication, request);
    storeAccessTokenInRedis(accessToken, userInfoJson);
    return accessToken;
  }

  public String createRefreshToken(Authentication authentication){
    Claims claims = generateRefreshClaims(authentication);
    String refreshToken = generateJwtToken(claims,refreshExpirationTime);
    storeRefreshTokenInRedis(refreshToken, authentication);
    return refreshToken;
  }

  public Authentication getAuthentication(String token,  HttpServletRequest request) {
    Claims claims = parseToken(token);
    Map<String, Object> userInfoMap = getUserInfoFromRedis(token);
    validateIncomingRequest(request, userInfoMap);
    Long userId = ((Number) userInfoMap.get("userId")).longValue();
    Long empId = ((Number) userInfoMap.get("empId")).longValue();
    UserDetails userDetails = userDetailsService.loadUserByUserIdAndEmpId(userId, empId);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private Map<String, Object> getUserInfoFromRedis(String token) {
    String userInfoJson = null;
    try {
      userInfoJson = redisTemplate.opsForValue().get(token);
      if (userInfoJson == null) { // 4. Null Check
        throw new BusinessLogicException(JwtExceptionCode.INVALID_REDIS_TOKEN);
      }
      return objectMapper.readValue(userInfoJson, Map.class);
    } catch (RedisException e) { // 1. Redis Connection Error
      throw new BusinessLogicException(JwtExceptionCode.NO_REDIS_CONNECTION);
    } catch (JsonProcessingException | ClassCastException e) {
      throw new BusinessLogicException(JwtExceptionCode.TYPE_MISMATCH);
    }
  }
  // 쿠키에 토큰을 저장하는 메서드
  public void setCookie(HttpServletResponse response, String name, String value) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    if (useHttps) {
      cookie.setSecure(true);
      cookie.setDomain(".amaranth2023.site");
    }
    response.addCookie(cookie);
  }

  // 쿠키에서 토큰을 삭제하는 메서드
  public void deleteCookie(HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");

    if (useHttps) {
      cookie.setSecure(true);
      cookie.setDomain(".amaranth2023.site");
    }
    response.addCookie(cookie);
  }
  private void validateIncomingRequest(HttpServletRequest request, Map<String, Object> userInfoMap) {
    String incomingIp = request.getRemoteAddr();
    String incomingUserAgent = request.getHeader("User-Agent");
    if (incomingIp == null ||incomingUserAgent == null) {
      throw new BusinessLogicException(JwtExceptionCode.MISSING_USER_AGENT);
    }
    String storedIp = (String) userInfoMap.get("clientIp");
    String storedUserAgent = (String) userInfoMap.get("userAgent");
    if (!storedIp.equals(incomingIp) || !storedUserAgent.equals(incomingUserAgent)) {
      throw new BusinessLogicException(JwtExceptionCode.MISMATCHED_USER_AGENT);
    }
  }

  public String getAccessTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      throw new BusinessLogicException(JwtExceptionCode.MISSING_COOKIE);
    }
    for (Cookie cookie : cookies)
      if ("accessToken".equals(cookie.getName()))
        return cookie.getValue();
    throw new BusinessLogicException(JwtExceptionCode.INVALID_COOKIE);
  }

  public String getRefreshTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      throw new BusinessLogicException(JwtExceptionCode.MISSING_COOKIE);
    }
    for (Cookie cookie : cookies)
      if ("refreshToken".equals(cookie.getName()))
        return cookie.getValue();
    throw new BusinessLogicException(JwtExceptionCode.INVALID_COOKIE);
  }

  public boolean validateToken(String token){
    try{
      System.out.println("validation");
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch(ExpiredJwtException e) {
      throw new BusinessLogicException(JwtExceptionCode.EXPIRED_JWT_TOKEN);
    } catch(JwtException e) {
      throw new BusinessLogicException(JwtExceptionCode.INVALID_JWT_TOKEN);
    }
  }

  public void logout(HttpServletRequest request) {
    // 클라이언트로부터 쿠키 혹은 헤더에서 토큰을 가져옵니다.
    String accessToken = getAccessTokenFromRequest(request);
    String refreshToken = getRefreshTokenFromRequest(request);

    if (accessToken == null && refreshToken == null) {
      throw new BusinessLogicException(JwtExceptionCode.MISSING_TOKENS);
    }

    if (accessToken != null) {
      if (!validateAndDeleteToken(accessToken)) {
        log.warn("유효한 액세스 토큰이 아니지만 로그아웃처리 하였습니다.");
      }
    }

    if (refreshToken != null) {
      if (!validateAndDeleteToken(refreshToken)) {
        log.warn("유효한 액세스 토큰이 아니지만 로그아웃처리 하였습니다.");
      }
    }

  }
  private boolean validateAndDeleteToken(String token) {
    if (validateToken(token)) {
      if (redisTemplate.hasKey(token)) {
        redisTemplate.delete(token);
        return true;
      } else {
        throw new BusinessLogicException(JwtExceptionCode.TOKEN_NOT_FOUND_IN_REDIS);
      }
    }
    return false;
  }
  private String generateJwtToken(Claims claims, long expirationTime) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expirationTime);
    return Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .compact();
  }

  private Claims generateAccessClaims(Authentication authentication) {
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    claims.put("empId", ((PrincipalDetails) authentication.getPrincipal()).getEmployeeId());
    return claims;
  }

  private Claims generateRefreshClaims(Authentication authentication) {
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    return claims;
  }

  private Claims parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private String generateUserInfoJson(Authentication authentication, HttpServletRequest request) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
    String clientIp = request.getRemoteAddr();
    String userAgent = request.getHeader("User-Agent");
    Map<String, Object> userInfoMap = new HashMap<>();
    userInfoMap.put("empId", principalDetails.getEmployeeId());
    userInfoMap.put("userId", principalDetails.getUserId());
    userInfoMap.put("compId", principalDetails.getCompanyId());
    userInfoMap.put("deptId", principalDetails.getDepartmentId());
    userInfoMap.put("clientIp", clientIp);
    userInfoMap.put("userAgent", userAgent);

    String userInfoJson = null;
    try {
      userInfoJson = objectMapper.writeValueAsString(userInfoMap);
    } catch (JsonProcessingException e) {
      throw new BusinessLogicException(JwtExceptionCode.JSON_PROCESSING_ERROR);
    }

    return userInfoJson;
  }

  private void storeAccessTokenInRedis(String accessToken, String userInfoJson) {
    redisTemplate.opsForValue().set(
      accessToken,
      userInfoJson,
      accessExpirationTime,
      TimeUnit.MILLISECONDS
    );
  }

  private void storeRefreshTokenInRedis(String refreshToken, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
    redisTemplate.opsForValue().set(
        refreshToken,
        principalDetails.toString(),
        refreshExpirationTime,
        TimeUnit.MILLISECONDS
    );
  }


}