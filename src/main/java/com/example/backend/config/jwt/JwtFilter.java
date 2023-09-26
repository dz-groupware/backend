package com.example.backend.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {
  private final JwtTokenProvider jwtTokenProvider;

  public JwtFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String accessToken = jwtTokenProvider.getAccessTokenFromRequest(request);
    String requestURI = request.getRequestURI();
    System.out.println("in jwt :: " + accessToken);


    if(!requestURI.startsWith("/api/v1/auth/login")){
      try {
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
          Authentication auth = jwtTokenProvider.getAuthentication(accessToken, request);
          SecurityContextHolder.getContext().setAuthentication(auth); // 정상 토큰이면 SecurityContext에 저장
          System.out.println("accetoken :: "+ auth);
        }
      } catch (ExpiredJwtException e) {
        System.out.println("catch1");
        // 여기서 리프레쉬 토큰 사용하여 새로운 액세스 토큰 발급
        String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
          Authentication auth = jwtTokenProvider.getAuthentication(refreshToken, request);
          SecurityContextHolder.getContext().setAuthentication(auth);
          String newAccessToken = jwtTokenProvider.createAccessToken(auth, request)[0];
          String newRefreshToken = jwtTokenProvider.createRefreshToken(auth);
          // 새 액세스 토큰을 응답 헤더나 쿠키 등에 추가
          Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
          Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
          refreshTokenCookie.setHttpOnly(true);
          refreshTokenCookie.setPath("/");

          response.addCookie(accessTokenCookie);
          response.addCookie(refreshTokenCookie);
        }
      } catch (RedisConnectionFailureException e) {
        SecurityContextHolder.clearContext();
        System.out.println("catch2");
        throw new Error("레디스 에러");
      } catch (JwtException e) {
        System.out.println("catch3");
        throw new Error("유효하지 않은 JWT");
      }
    }
    System.out.println("good");
    filterChain.doFilter(request, response);
  }
}
