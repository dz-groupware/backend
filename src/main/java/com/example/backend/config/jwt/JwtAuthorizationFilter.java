package com.example.backend.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backend.user.UserMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final String jwtKey;
  private final UserMapper userMapper;

  public JwtAuthorizationFilter(String jwt_key, UserMapper userMapper) {
    this.jwtKey = jwt_key;
    this.userMapper = userMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    System.out.println("#########################");
    System.out.println("#########################");
    System.out.println("#########################");
    System.out.println("#########################필터들어옴");
//    if ("/api/v1/login".equals(request.getRequestURI())) {
//      // 로그인 요청이므로 다르게 처리
//      chain.doFilter(request, response);
//      return;
//    }
//
//    if (!isValidAuthorizationHeader(request, response, chain)) {
//      return;
//    }

    String accessToken = getAccessTokenFromCookie(request);
    if (accessToken == null || "".equals(accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }


    String username = verifyAccessToken(accessToken, request, response, chain);
    if (username == null) {
      return;
    }

    setSecurityContext(response, username);
    chain.doFilter(request, response);
  }

  private void setSecurityContext(HttpServletResponse response, String username) {
    PrincipalUserDto principalUser = userMapper.findByLoginId(username);
    System.out.println("#######################"+ principalUser.getUserId());
    System.out.println("#######################"+ principalUser.getUserId());
    System.out.println("#######################"+ principalUser.getUserId());
    System.out.println("#######################"+ principalUser.getUserId());
    System.out.println("#######################"+ principalUser.getUserId());
    System.out.println("#######################"+ principalUser.getUserId());
    if (principalUser == null) {
      response.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
      response.setStatus(403);
      return;
    }

    PrincipalDetails principalDetails = new PrincipalDetails(principalUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
        principalDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

//  private String getAccessTokenFromHeader(HttpServletRequest request) {
//    String authorizationHeader = request.getHeader("Authorization");
//    return authorizationHeader.replace("Bearer ", "");
//  }

  private String getAccessTokenFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("JWT".equals(cookie.getName())) {
          System.out.println("#####################");
          System.out.println("#####################");
          System.out.println("#####################");
          System.out.println("#####################" + cookie.getValue());
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  private boolean isValidAuthorizationHeader(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
      response.setHeader("WWW-Authenticate", "Bearer realm=\"example\"");
      chain.doFilter(request, response);
      return false;
    }
    return true;
  }

  private String verifyAccessToken(String accessToken, HttpServletRequest request,
      HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      return JWT.require(Algorithm.HMAC512(jwtKey)).build().verify(accessToken).getClaim("sub").asString();
    } catch (Exception e) {
      System.out.println(JWT.require(Algorithm.HMAC512(jwtKey)).build().verify(accessToken).getClaim("sub").asString());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      chain.doFilter(request, response);  // 여기를 추가
      return null;
    }
  }
}