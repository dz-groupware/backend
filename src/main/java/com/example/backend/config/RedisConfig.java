package com.example.backend.config;

import com.example.backend.setting.dto.JwtDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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

  @Bean(name = "redisTemplate")
  public RedisTemplate<String, String> redisTemplate(@Qualifier("factoryForMenu")RedisConnectionFactory redisConnectionFactory) {
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

  @Bean(name = "redisForPayload")
  public RedisTemplate<String, JwtDto> redisForPayload(@Qualifier("factoryForPayload")RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, JwtDto> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    Jackson2JsonRedisSerializer<JwtDto> jsonSerializer = new Jackson2JsonRedisSerializer<>(JwtDto.class);
    template.setValueSerializer(jsonSerializer);
    template.setHashValueSerializer(jsonSerializer);
    return template;
  }

  @Bean(name = "factoryForMenu")
  public RedisConnectionFactory factoryForMenu(){
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
    lettuceConnectionFactory.setDatabase(1); // menuId Set 조회용
    return lettuceConnectionFactory;
  }

  @Bean(name = "factoryForPayload")
  public RedisConnectionFactory factoryForPayload(){
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
    lettuceConnectionFactory.setDatabase(2); // payload 조회용
    return lettuceConnectionFactory;
  }
}
