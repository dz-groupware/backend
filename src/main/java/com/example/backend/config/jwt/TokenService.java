package com.example.backend.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  @Value("${jwt.secret.key}")
  private String jwtKey;

  public String createToken(PrincipalDetails principalDetails) {
    String jwtToken = Jwts.builder()
        .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
        .claim("empId", principalDetails.getEmployeeId().toString())
        .signWith(Keys.hmacShaKeyFor(jwtKey), SignatureAlgorithm.HS512)
        .compact();

    return JWT.create()
        .withSubject(principalDetails.getUsername()) // 토큰 이름 설정
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .withClaim("empId", principalDetails.getEmployeeId().toString())

//        .withClaim("userId", principalDetails.getUserId())
//        .withClaim("empId", principalDetails.getEmployeeId())
//        .withClaim("compId", principalDetails.getCompanyId())
//        .withClaim("deptId", principalDetails.getDepartmentId())
        .sign(Algorithm.HMAC512(jwtKey)); // 고유한 시크릿 값 적용

  }
  public Cookie createJwtCookie(String token) {
    Cookie jwtCookie = new Cookie("JWT", token);
    jwtCookie.setMaxAge(60*60*24*7); // 7 days
    jwtCookie.setPath("/");
    jwtCookie.setHttpOnly(true);
    return jwtCookie;
  }

}
