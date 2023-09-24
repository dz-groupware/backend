package com.example.backend.config;

import com.example.backend.config.jwt.AuthorizationMenuFilter;
import com.example.backend.common.dto.PkDto;
import com.example.backend.redis.RedisMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class FilterConfig {
  @Value("${jwt.secret.key}")
  private String jwtKey;
  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisForMenu;
  private final RedisTemplate<String, PkDto> redisForPayload;

  public FilterConfig(RedisMapper redisMapper, RedisTemplate<String, String> redisForMenu, RedisTemplate<String, PkDto> redisForPayload) {
    this.redisMapper = redisMapper;
    this.redisForMenu = redisForMenu;
    this.redisForPayload = redisForPayload;

  }
  @Bean
  public FilterRegistrationBean<AuthorizationMenuFilter> AuthorizationMenuFilterBean() {
    FilterRegistrationBean<AuthorizationMenuFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthorizationMenuFilter(jwtKey, redisForMenu, redisMapper, redisForPayload));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Integer.MAX_VALUE); // 가장 마지막에 실행
    return registrationBean;
  }

}
