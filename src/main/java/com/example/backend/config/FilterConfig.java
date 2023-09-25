package com.example.backend.config;

import com.example.backend.config.jwt.AuthorizationMenuFilter;
import com.example.backend.config.jwt.JwtTokenProvider;
import com.example.backend.config.jwt.PkDto;
import com.example.backend.redis.RedisMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class FilterConfig {
  @Value("${spring.jwt.secret.key}")
  private String jwtKey;
  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisForMenu;
  private final RedisTemplate<String, PkDto> redisForPayload;
  private final JwtTokenProvider tokenProvider;

  public FilterConfig(RedisMapper redisMapper, RedisTemplate<String, String> redisForMenu,
      RedisTemplate<String, PkDto> redisForPayload,
      JwtTokenProvider tokenProvider) {
    this.redisMapper = redisMapper;
    this.redisForMenu = redisForMenu;
    this.redisForPayload = redisForPayload;
    this.tokenProvider = tokenProvider;
  }
  @Bean
  public FilterRegistrationBean<AuthorizationMenuFilter> AuthorizationMenuFilterBean() {
    FilterRegistrationBean<AuthorizationMenuFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthorizationMenuFilter(jwtKey, redisForMenu, redisMapper, redisForPayload,tokenProvider ));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Integer.MAX_VALUE); // 가장 마지막에 실행
    return registrationBean;
  }

}
