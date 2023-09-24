package com.example.backend.config.jwt;

import com.example.backend.common.dto.SingleResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final String jwtKey;
  private final AuthenticationManager authenticationManager;
  private final ObjectMapper objectMapper;
  private final TokenService tokenService;
  public JwtAuthenticationFilter(String jwtKey, AuthenticationManager authenticationManager,
      ObjectMapper objectMapper, TokenService tokenService) {
    this.jwtKey = jwtKey;
    this.authenticationManager = authenticationManager;
    this.objectMapper = objectMapper;
    this.tokenService = tokenService;
    setFilterProcessesUrl("/login"); // 로그인 URL 변경
  }
  // ("/login") 요청을 하면 로그인 시도를 위해서 실행되는 함수
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {

      LoginReqDto loginReqDto = objectMapper.readValue(request.getInputStream(), LoginReqDto.class);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          loginReqDto.getLoginId(), loginReqDto.getLoginPw());

      // authenticationManager 로 로그인 시도를 하면
      // principalDetailsService 의 loadUserByUsername() 가 실행된 후 정상이면 authentication 이 리텀됨
      Authentication authentication = authenticationManager.authenticate(authenticationToken);

      System.out.println("로그인 완료");
      return authentication;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  // attemptAuthentication() 실행 후 인증이 정상적으로 되었으면 successfulAuthentication() 실행
  // JWT 토크을 만들어서 request 요청한 사용자에게 JWT 토큰을 response
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
    String jwtToken = tokenService.createToken(principalDetails);

    //헤더
    response.addHeader("Authorization", "Bearer " + jwtToken);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    //쿠키
    Cookie jwtCookie = tokenService.createJwtCookie(jwtToken);
    response.addCookie(jwtCookie);

    Map<String, Object> responseMap = new HashMap<>();
    SingleResponseDto<PrincipalUserDto> responseBody = new SingleResponseDto<>(
        principalDetails.getPrincipalUser());

    responseMap.put("status", HttpServletResponse.SC_OK);
    responseMap.put("data",responseBody);

    response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    response.getWriter().flush();
  }

  @Override
  protected AuthenticationSuccessHandler getSuccessHandler() {
    return super.getSuccessHandler();
  }
}