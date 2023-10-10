package com.example.backend.config.jwt;

import com.example.backend.common.error.BusinessLogicException;
import com.example.backend.common.error.code.JwtExceptionCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.lettuce.core.RedisException;
import java.util.Arrays;
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

  public Authentication getAuthentication(String token,  HttpServletRequest request) {
    Claims claims = parseToken(token); //토큰이 해독되면
    Map<String, Object> userInfoMap = getUserInfoFromRedis(token, request);
//    validateIncomingRequest(request, userInfoMap);
    Long userId = ((Number) userInfoMap.get("userId")).longValue();
    Long empId = ((Number) userInfoMap.get("empId")).longValue();
    UserDetails userDetails = userDetailsService.loadUserByUserIdAndEmpId(userId, empId);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private Map<String, Object> getUserInfoFromRedis(String token, HttpServletRequest request) {
    try {
      String userInfoJson = redisTemplate.opsForValue().get(token);
      log.info("레디스에서 읽어온 데이터"+userInfoJson);
      if (userInfoJson == null) {
        // 레디스에서 데이터를 찾을 수 없으면 DB에서 사용자 정보 가져오기
        Claims claims = parseToken(token);
        Long userId = ((Number) claims.get("userId")).longValue();
        Long empId = ((Number) claims.get("empId")).longValue();
        UserDetails userDetails = userDetailsService.loadUserByUserIdAndEmpId(userId, empId);

        // DB에서 가져온 사용자 정보를 Authentication 객체로 변환
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        // Authentication 객체를 사용하여 사용자 정보를 JSON 형태로 생성
        userInfoJson = generateUserInfoJson(authentication, request);
        // 생성된 사용자 정보를 레디스에 저장
        redisTemplate.opsForValue().set(token, userInfoJson, accessExpirationTime, TimeUnit.MILLISECONDS);
        log.info("레디스에 사용자 정보 저장: " + userInfoJson);
      }
      return objectMapper.readValue(userInfoJson, Map.class);
    } catch (RedisException e) {
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
    cookie.setMaxAge(43200);
    if (useHttps) {
      cookie.setSecure(true);
      cookie.setDomain("amaranth2023.site");
    }
    response.addCookie(cookie);
    log.info("쿠키출력" + cookie.toString());
  }

  // 쿠키에서 토큰을 삭제하는 메서드
  public void deleteCookie(HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");

    if (useHttps) {
      cookie.setSecure(true);
      cookie.setDomain("amaranth2023.site");
    }
    response.addCookie(cookie);
  }


  public String getAccessTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      log.warn("쿠키가 없습니다.");
      throw new BusinessLogicException(JwtExceptionCode.MISSING_COOKIE);
    }
    log.info(Arrays.toString(cookies));
    for (Cookie cookie : cookies)
      if ("accessToken".equals(cookie.getName())){
        log.info("find accessToken");
        return cookie.getValue();
      }
    log.info("now Throw Error : INVALID_COOKIE ");
    throw new BusinessLogicException(JwtExceptionCode.INVALID_COOKIE);
  }

  public boolean validateToken(String token,HttpServletResponse response){
    try{
      log.info("validate token "+ token);
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch(ExpiredJwtException e) {
      log.error("만료된 토큰입니다.");
      deleteCookie(response, token);
      throw new BusinessLogicException(JwtExceptionCode.EXPIRED_JWT_TOKEN);
    } catch(JwtException e) {
      log.error("유효하지 않은 토큰입니다.");
      throw new BusinessLogicException(JwtExceptionCode.INVALID_JWT_TOKEN);
    }
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
    claims.put("userId", ((PrincipalDetails) authentication.getPrincipal()).getUserId());
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
    System.out.println(principalDetails.toString());
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
    log.info("레디스에 토큰 저장중");
    try {
      redisTemplate.opsForValue().set(accessToken, userInfoJson, accessExpirationTime/12, TimeUnit.MILLISECONDS);
      String storedData = redisTemplate.opsForValue().get(accessToken);
      if (storedData != null) {
        log.info("성공 후 저장된 데이터: " + storedData);
      } else {
        log.warn("레디스에 데이터 저장에 실패했습니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.warn("레디스에 데이터를 저장하는 중에 문제가 발생했습니다: " + e.getMessage());
    }
  }

  //  public String createRefreshToken(Authentication authentication){
//    Claims claims = generateRefreshClaims(authentication);
//    String refreshToken = generateJwtToken(claims,accessExpirationTime);
//    storeRefreshTokenInRedis(refreshToken, authentication);
//    return refreshToken;
//  }

  //  public void logout(HttpServletRequest request) {
//    // 클라이언트로부터 쿠키 혹은 헤더에서 토큰을 가져옵니다.
//    String accessToken = getAccessTokenFromRequest(request);
//    String refreshToken = getRefreshTokenFromRequest(request);
//
//    if (accessToken == null && refreshToken == null) {
//      throw new BusinessLogicException(JwtExceptionCode.MISSING_TOKENS);
//    }
//
//    if (accessToken != null) {
//      if (!validateAndDeleteToken(accessToken)) {
//        log.warn("유효한 액세스 토큰이 아니지만 로그아웃처리 하였습니다.");
//      }
//    }
//
//    if (refreshToken != null) {
//      if (!validateAndDeleteToken(refreshToken)) {
//        log.warn("유효한 액세스 토큰이 아니지만 로그아웃처리 하였습니다.");
//      }
//    }
//  }
//  private boolean validateAndDeleteToken(String token,HttpServletResponse response) {
//    if (redisTemplate.hasKey(token)) {
//      redisTemplate.delete(token);
//    }
//    if (validateToken(token, response)) {
//      return true;
//    } else {
//      log.warn("유효하지 않은 토큰입니다.");
//      return false;
//    }
//  }

//  private void storeRefreshTokenInRedis(String refreshToken, Authentication authentication) {
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//    PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
//    redisTemplate.opsForValue().set(
//        refreshToken,
//        principalDetails.toString(),
//        refreshExpirationTime,
//        TimeUnit.MILLISECONDS
//    );
//  }


//  public String getRefreshTokenFromRequest(HttpServletRequest request) {
//    Cookie[] cookies = request.getCookies();
//    if (cookies == null) {
//      throw new BusinessLogicException(JwtExceptionCode.MISSING_COOKIE);
//    }
//    for (Cookie cookie : cookies)
//      if ("refreshToken".equals(cookie.getName()))
//        return cookie.getValue();
//    throw new BusinessLogicException(JwtExceptionCode.INVALID_COOKIE);
//  }

  //
//  private Claims generateRefreshClaims(Authentication authentication) {
//    Claims claims = Jwts.claims().setSubject(authentication.getName());
//    return claims;
//  }

//  private void validateIncomingRequest(HttpServletRequest request, Map<String, Object> userInfoMap) {
//    String incomingIp = request.getRemoteAddr();
//    String incomingUserAgent = request.getHeader("User-Agent");
//    if (incomingIp == null ||incomingUserAgent == null) {
//      throw new BusinessLogicException(JwtExceptionCode.MISSING_USER_AGENT);
//    }
//    String storedIp = (String) userInfoMap.get("clientIp");
//    String storedUserAgent = (String) userInfoMap.get("userAgent");
//    if (!storedIp.equals(incomingIp) || !storedUserAgent.equals(incomingUserAgent)) {
//      throw new BusinessLogicException(JwtExceptionCode.MISMATCHED_USER_AGENT);
//    }
//  }

}