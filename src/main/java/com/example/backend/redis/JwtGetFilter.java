package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtGetFilter extends OncePerRequestFilter {

  private final String jwtKey;


  public JwtGetFilter(String jwtKey) {
    this.jwtKey = jwtKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    // 토큰 받아와서
    String accessToken = getAccessTokenFromCookie(request);
    if (accessToken == null || "".equals(accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    JwtDto jwtDto = getInfo(accessToken);

    jwtDto.setJwt(accessToken);

    request.setAttribute("JWT", jwtDto);

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

  public JwtDto getInfo(String accessToken) throws JsonProcessingException {
    String resultJWT = new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
    JwtDto jwtDto = new ObjectMapper().readValue(resultJWT, JwtDto.class);
    return jwtDto;
  }
}
