package com.example.backend.config;

import com.example.backend.config.jwt.JwtAuthenticationFilter;
import com.example.backend.config.jwt.JwtAuthorizationFilter;
import com.example.backend.employee.mapper.EmployeeMapper;
import com.example.backend.user.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true) // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터 체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// @Secured 활성화, @PreAuthorize & @PostAuthorize 활성화
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsFilter corsFilter;
  private final UserMapper userMapper;
  private final ObjectMapper objectMapper;

  @Value("${jwt.secret.key}")
  private String jwtKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers().frameOptions()
        .sameOrigin()// X-Frame-Options를 sameOrigin으로 설정합니다. 이를 통해 동일 출처에서만 iframe을 허용합니다.
        .and()
        .addFilter(corsFilter) // CORS 필터 추가
        .csrf().disable() // CSRF 보호 비활성화
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않도록 설정 (JWT 사용)
        .and()
        .formLogin().disable() // 폼 기반 로그인 비활성화
        .httpBasic().disable() // HTTP Basic 인증 비활성화
        .exceptionHandling()
//                .authenticationEntryPoint(entryPoint) // 사용자 정의 인증 진입점 설정
        .and()
        .apply(new CustomFilterConfigurer(jwtKey, userMapper)) // 사용자 정의 필터 적용
        .and()
        .authorizeRequests(authorize -> authorize // 인증 규칙 정의
                .antMatchers(
                                "/api/v1/login"
                ).permitAll()
                .anyRequest().authenticated()
        );

    return http.build();
  }

  public class CustomFilterConfigurer extends
      AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

    private final String jwtKey;
    private final UserMapper userMapper;

    public CustomFilterConfigurer(String jwtKey, UserMapper userMapper) {
      this.jwtKey = jwtKey;
      this.userMapper = userMapper;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager = builder.getSharedObject(
          AuthenticationManager.class);
      builder
          .addFilterBefore(new JwtAuthenticationFilter(jwtKey, authenticationManager, objectMapper),
              UsernamePasswordAuthenticationFilter.class)
          .addFilterAfter(new JwtAuthorizationFilter(jwtKey, userMapper),
              UsernamePasswordAuthenticationFilter.class);
    }
  }

}