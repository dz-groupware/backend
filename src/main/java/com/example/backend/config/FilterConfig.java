package com.example.backend.config;

import com.example.backend.redis.AuthorizationMenuFilter;
import com.example.backend.redis.RedisMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class FilterConfig {
  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisForMenu;

  public FilterConfig(RedisMapper redisMapper, RedisTemplate<String, String> redisForMenu) {
    this.redisMapper = redisMapper;
    this.redisForMenu = redisForMenu;
  }
  @Bean
  public FilterRegistrationBean<AuthorizationMenuFilter> AuthorizationMenuFilterBean() {
    FilterRegistrationBean<AuthorizationMenuFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthorizationMenuFilter(redisForMenu, redisMapper));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }

}
