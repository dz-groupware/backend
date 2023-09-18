package com.example.backend.config.redis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtTokenProvider {

//  private final RedisTemplate<String, String> redisTemplate;

  @Value("${spring.jwt.secret.key}")
  private String secretKey;

  @Value("${spring.jwt.token.access-expiration-time}")
  private long accessExpirationTime;

  @Value("${spring.jwt.token.refresh-expiration-time}")
  private long refreshExpirationTime;

  private final PrincipalDetailsService userDetailsService;

  /**
   * Access 토큰 생성
   */
  public String createAccessToken(Authentication authentication){
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
    claims.put("empId", principalDetails.getEmployeeId());
    claims.put("userId", principalDetails.getUserId());
    claims.put("compId", principalDetails.getCompanyId());
    claims.put("deptId", principalDetails.getDepartmentId());
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + accessExpirationTime);

    String accessToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();

    // redis에 저장
//    redisTemplate.opsForValue().set(
//        "AT:" + authentication.getName(), // Key 값을 'AT:(사용자이름)' 형태로 지정
//        accessToken,
//        accessExpirationTime,
//        TimeUnit.MILLISECONDS
//    );

    return accessToken;
  }

  /**
   * Refresh 토큰 생성
   */
  public String createRefreshToken(Authentication authentication){
    Claims claims = Jwts.claims().setSubject(authentication.getName());
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + refreshExpirationTime);

    String refreshToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();

    // redis에 저장
//    redisTemplate.opsForValue().set(
//        authentication.getName(),
//        refreshToken,
//        refreshExpirationTime,
//        TimeUnit.MILLISECONDS
//    );

    return refreshToken;
  }

  /**
   * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
   */
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
      System.out.println(claims.toString());

    Long userId = ((Number) claims.get("userId")).longValue();
    Long empId = ((Number) claims.get("empId")).longValue();
    UserDetails userDetails = userDetailsService.loadUserByUserIdAndEmpId(userId, empId);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * 쿠키로부터 토큰 가져오기
   */
  public String getAccessTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
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
  /**
   * Access 토큰을 검증
   */
  public boolean validateToken(String token){
    try{
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch(ExpiredJwtException e) {
      throw new Error("만료된토큰");
    } catch(JwtException e) {
      throw new Error("유효하지않은 토큰");
    }
  }
}