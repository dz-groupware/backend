package com.example.backend.config;

import com.example.backend.config.jwt.PkDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableRedisRepositories
@EnableTransactionManagement
public class RedisConfig {

  @Value("${spring.redis.port}")
  private int port;

  @Value("${spring.redis.host}")
  private String host;

  @Bean(name = "redisTemplate")
  public RedisTemplate<String, String> redisTemplate(@Qualifier("redisConnectionFactory")RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    return template;
  }

  @Bean(name = "redisForMenu")
  public RedisTemplate<String, String> redisForMenu(@Qualifier("factoryForMenu")RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    return template;
  }

  @Bean(name = "redisConnectionFactory")
  public RedisConnectionFactory redisConnectionFactory(){
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
    lettuceConnectionFactory.setDatabase(1);
    return lettuceConnectionFactory;
  }

  @Bean(name = "factoryForMenu")
  public RedisConnectionFactory factoryForMenu(){
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
    lettuceConnectionFactory.setDatabase(3); // menuId Set 조회용
    return lettuceConnectionFactory;
  }
}
