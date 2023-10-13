package com.example.backend.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("redisToken")
@AllArgsConstructor
@Builder
public class RedisToken {
  @Id
  private String id;

  @Indexed
  private String username;

  @TimeToLive
  private Long expiration; // seconds
  @Value("${spring.jwt.token.access-expiration-time}")
  private long accessExpirationTime;

  public static RedisToken createRedisToken(String jwtString, String AdminAndMenu, Long accessExpirationTime){
    return RedisToken.builder()
        .id(jwtString)
        .username(AdminAndMenu)
        .expiration(accessExpirationTime/12)
        .build();
  }
}
