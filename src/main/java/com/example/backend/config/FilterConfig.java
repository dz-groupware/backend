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
  private final RedisMapper redisMapper;

  public FilterConfig(RedisMapper redisMapper) {
    this.redisMapper = redisMapper;
  }
  @Bean
  public FilterRegistrationBean<AuthorizationMenuFilter> AuthorizationMenuFilterBean() {
    FilterRegistrationBean<AuthorizationMenuFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthorizationMenuFilter(redisMapper));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Integer.MAX_VALUE); // 가장 마지막에 실행
    return registrationBean;
  }

}
