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

    String requestURI = request.getRequestURI();
    // requestURI.startsWith("/api/v1/test") : 테스트 용 요청은 필터 확인 안하도록 변경 (추후 삭제될 수 있음)
//    if (requestURI.startsWith("/api/v1/test")){
//      filterChain.doFilter(request, response);
//      return;
//    }
    logger.info(Thread.currentThread().getName());
    if(!requestURI.startsWith("/api/v1/auth/login")){
      String accessToken = jwtTokenProvider.getAccessTokenFromRequest(request);
      try {
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
          Authentication auth = jwtTokenProvider.getAuthentication(accessToken, request);
          SecurityContextHolder.getContext().setAuthentication(auth); // 정상 토큰이면 SecurityContext에 저장
        }
      } catch (ExpiredJwtException e) {
        // 여기서 리프레쉬 토큰 사용하여 새로운 액세스 토큰 발급
        String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
          Authentication auth = jwtTokenProvider.getAuthentication(refreshToken, request);
          SecurityContextHolder.getContext().setAuthentication(auth);
          String newAccessToken = jwtTokenProvider.createAccessToken(auth, request);
          String newRefreshToken = jwtTokenProvider.createRefreshToken(auth);
          // 새 액세스 토큰을 응답 헤더나 쿠키 등에 추가
          Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
          Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
          accessTokenCookie.setHttpOnly(true);
          accessTokenCookie.setPath("/");
          refreshTokenCookie.setHttpOnly(true);
          refreshTokenCookie.setPath("/");

          response.addCookie(accessTokenCookie);
          response.addCookie(refreshTokenCookie);
        }
      } catch (RedisConnectionFailureException e) {
        SecurityContextHolder.clearContext();
        throw new Error("레디스 에러");
      } catch (JwtException e) {
        throw new Error("유효하지 않은 JWT");
      }
    }
    filterChain.doFilter(request, response);
  }
}
