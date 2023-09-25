package com.example.backend.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

  private final RedisTemplate<String, String> redisTemplate;

  @Value("${spring.jwt.secret.key}")
  private String secretKey;

  @Value("${spring.jwt.token.access-expiration-time}")
  private long accessExpirationTime;

  @Value("${spring.jwt.token.refresh-expiration-time}")
  private long refreshExpirationTime;

  private final PrincipalDetailsService userDetailsService;
  private final ObjectMapper objectMapper;

  public String createAccessToken(Authentication authentication, HttpServletRequest request)  {
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + accessExpirationTime);
    claims.put("empId", principalDetails.getEmployeeId());

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
      e.printStackTrace();
    }

    String accessToken = Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .compact();

    redisTemplate.opsForValue().set(
        accessToken, // key
        userInfoJson,
        accessExpirationTime,
        TimeUnit.MILLISECONDS
    );

    return accessToken;
  }

  public String createRefreshToken(Authentication authentication){
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + refreshExpirationTime);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    PrincipalDetails principalDetails = (PrincipalDetails) userDetails;

    String refreshToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expireDate)
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();

    redisTemplate.opsForValue().set(
        refreshToken,
        principalDetails.toString(),
        refreshExpirationTime,
        TimeUnit.MILLISECONDS
    );

    return refreshToken;
  }

  public Authentication getAuthentication(String token,  HttpServletRequest request) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody();

    // 요청에서 오는 IP와 User-Agent 정보
    String incomingIp = request.getRemoteAddr();
    String incomingUserAgent = request.getHeader("User-Agent");

    String userInfoJson = redisTemplate.opsForValue().get(token);
    // JSON 문자열을 Map으로 변환
    Map<String, Object> userInfoMap = null;
    try {
      userInfoMap = objectMapper.readValue(userInfoJson, Map.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    // 토큰에 저장된 IP와 User-Agent 정보
    Long userId = ((Number) userInfoMap.get("userId")).longValue();
    Long empId = ((Number) userInfoMap.get("empId")).longValue();
    String storedIp = (String) userInfoMap.get("clientIp");
    String storedUserAgent = (String) userInfoMap.get("userAgent");
    System.out.println("들어온거 +" + incomingIp);
    System.out.println("들어온거 +" + incomingUserAgent);
    System.out.println("저장된거 +" + storedIp);
    System.out.println("저장된거 +" + storedUserAgent);
    if(!storedIp.equals(incomingIp) || !storedUserAgent.equals(incomingUserAgent)) {
      throw new RuntimeException("인증 정보가 일치하지 않습니다.");
    }

    UserDetails userDetails = userDetailsService.loadUserByUserIdAndEmpId(userId, empId);

    System.out.println("##############");
    System.out.println("userInfoMap: " + userInfoMap);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getAccessTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        System.out.println(cookie.getName());
        if ("accessToken".equals(cookie.getName())) {
          System.out.println("cooke.getValue : " + cookie.getValue());
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public String getRefreshTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("refreshToken".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public boolean validateToken(String token){
    try{
      System.out.println("validation");
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(token);
      System.out.println("에러뜨기전");
      return true;
    } catch(ExpiredJwtException e) {
      throw new Error("만료된토큰");
    } catch(JwtException e) {
      throw new Error("유효하지않은 토큰");
    }
  }

  public void logout(HttpServletRequest request) {
    // 클라이언트로부터 쿠키 혹은 헤더에서 토큰을 가져옵니다.
    String accessToken = getAccessTokenFromRequest(request);
    String refreshToken = getRefreshTokenFromRequest(request);

    if (accessToken != null) {
      if (validateToken(accessToken) && redisTemplate.hasKey(accessToken)) {
        redisTemplate.delete(accessToken);
      } else {
        log.warn("유효한토큰이 아니라 로그아웃을 할 수 없습니다.");
      }
    }

    if (refreshToken != null) {
      if (validateToken(refreshToken) && redisTemplate.hasKey(refreshToken)) {
        redisTemplate.delete(refreshToken);
      } else {
        log.warn("유효한토큰이 아니라 로그아웃을 할 수 없습니다.");
      }
    }

  }

}