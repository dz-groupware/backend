package com.example.backend.config.jwt;

import com.example.backend.common.error.BusinessLogicException;
import com.example.backend.common.error.ErrorResponse;
import com.example.backend.common.error.code.JwtExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
  private final JwtTokenProvider jwtTokenProvider;

  public JwtFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String requestURI = request.getRequestURI();

    if(!requestURI.startsWith("/api/v1/auth/login")){
      String accessToken= null;
      try {
        accessToken = jwtTokenProvider.getAccessTokenFromRequest(request);
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken, response)) {
          log.info("JWTFILTER access" + accessToken);
          Authentication auth = jwtTokenProvider.getAuthentication(accessToken, request);
          SecurityContextHolder.getContext().setAuthentication(auth); // 정상 토큰이면 SecurityContext에 저장
        }
      }
      catch (BusinessLogicException | ExpiredJwtException e) {
          System.out.println("JwtFilter 걸러짐");
          jwtTokenProvider.deleteCookie(response,accessToken);
          ErrorResponse.setToResponse(response, HttpStatus.PAYMENT_REQUIRED, e.getMessage());
        return;
      } catch (RedisConnectionFailureException e) {
        SecurityContextHolder.clearContext();
        ErrorResponse.setToResponse(response, HttpStatus.PAYMENT_REQUIRED, e.getMessage());
        return;
      } catch (JwtException e) {
        ErrorResponse.setToResponse(response, HttpStatus.PAYMENT_REQUIRED, e.getMessage());
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}
