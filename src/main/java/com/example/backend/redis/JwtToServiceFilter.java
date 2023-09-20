package com.example.backend.redis;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtToServiceFilter extends OncePerRequestFilter {

  private final String jwtKey;
  private static ThreadLocal<String> jwtThreadLocal = new ThreadLocal<>();

  public JwtToServiceFilter(String jwt_key) {
    this.jwtKey = jwt_key;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    String accessToken = getAccessTokenFromCookie(request);
    if (accessToken == null || "".equals(accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    jwtThreadLocal.set(accessToken);

    chain.doFilter(request, response);
  }

  private String getAccessTokenFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("JWT".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public String getJwtToken(){
    return jwtThreadLocal.get();
  }
}