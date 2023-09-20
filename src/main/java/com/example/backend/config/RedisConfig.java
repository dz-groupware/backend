package com.example.backend.config;

import com.example.backend.redis.JwtToServiceFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableRedisRepositories
@EnableTransactionManagement
public class RedisConfig {

  @Value("${spring.redis.port}")
  private int port;

  @Value("${spring.redis.host}")
  private String host;

  @Value("${jwt.secret.key}")
  private String jwtKey;

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    return template;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory(){
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
    return lettuceConnectionFactory;
  }

  @Bean
  public FilterRegistrationBean<JwtToServiceFilter> jwtFilterForService() {
    FilterRegistrationBean<JwtToServiceFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new JwtToServiceFilter(jwtKey));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }

  @Bean
  public JwtToServiceFilter jwtToServiceFilter(@Value("${jwt.secret.key}") String jwtKey) {
    return new JwtToServiceFilter(jwtKey);
  }
}
