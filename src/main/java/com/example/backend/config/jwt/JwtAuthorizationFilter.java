package com.example.backend.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.config.auth.PrincipalDetails;
import com.example.backend.employee.dto.EmployeeResDto;
import com.example.backend.employee.mapper.EmployeeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final String JWT_KEY;
  private final EmployeeMapper employeeMapper;
  private final ObjectMapper objectMapper;

  public JwtAuthorizationFilter(String jwt_key, EmployeeMapper employeeMapper,
      ObjectMapper objectMapper) {
    this.JWT_KEY = jwt_key;
    this.employeeMapper = employeeMapper;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    if (!isValidAuthorizationHeader(request, response, chain)) {
      return;
    }

    String accessToken = getAccessTokenFromHeader(request);
    String username = verifyAccessToken(accessToken, request, response, chain);
    if (username == null) {
      return;
    }

    setSecurityContext(response, username);
    chain.doFilter(request, response);
  }

  private void setSecurityContext(HttpServletResponse response, String username) {
    EmployeeResDto employee = employeeMapper.findByLoginId(username);
    if (employee == null) {
      response.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
      response.setStatus(403);
      return;
    }
    PrincipalDetails principalDetails = new PrincipalDetails(employee);
    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
        principalDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getAccessTokenFromHeader(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    return authorizationHeader.replace("Bearer ", "");
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
    return JWT.require(Algorithm.HMAC512(JWT_KEY)).build().verify(accessToken).getClaim("sub")
        .asString();
  }

  private String createAccessToken(HttpServletResponse response, DecodedJWT refreshJwt) {
    String username;
    String nickname;
    String id;
    username = refreshJwt.getClaim("sub").asString();
    id = refreshJwt.getClaim("id").asString();
    nickname = refreshJwt.getClaim("nickname").asString();

    return JWT.create()
        .withSubject(username)
        .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * 30)))
        .withClaim("id", id)
        .withClaim("nickname", nickname)
        .sign(Algorithm.HMAC512(JWT_KEY));
  }
}
