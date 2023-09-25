package com.example.backend.config;
import com.example.backend.config.jwt.JwtAccessDeniedHandler;
import com.example.backend.config.jwt.JwtAuthenticationEntryPoint;
import com.example.backend.config.jwt.JwtFilter;
import com.example.backend.config.jwt.JwtTokenProvider;
import com.example.backend.user.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
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
  private final JwtTokenProvider jwtTokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .headers().frameOptions()
        .sameOrigin()// X-Frame-Options를 sameOrigin으로 설정합니다. 이를 통해 동일 출처에서만 iframe을 허용합니다.
        .and()
        .addFilter(corsFilter)
        .formLogin().disable()
        .httpBasic().disable()  // 비인증시 login form redirect X (rest api)
        .csrf().disable()       // crsf 보안 X (rest api)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증 > 세션 필요없음
        .and()
        .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
//                .requestMatchers("/**").permitAll() // 모든 주소 허용
        .antMatchers("/auth/login").permitAll() // 허용된 주소
        .anyRequest().authenticated() // Authentication 필요한 주소
        .and()                  // exception handling for jwt
        .exceptionHandling()
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);

    // jwt 적용
    http.apply(new JwtSecurityConfig(jwtTokenProvider));
    return http.build();
  }

  @RequiredArgsConstructor
  private static class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
      JwtFilter customFilter = new JwtFilter(jwtTokenProvider);
      http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }
}