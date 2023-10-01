package com.example.backend.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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

  @Indexed // 필드 값으로 데이터 찾을 수 있게 하는 어노테이션(findByAccessToken)
  private String username;

  @TimeToLive
  private Long expiration; // seconds

  public static RedisToken createRedisToken(String jwtString, String AdminAndMenu, Long remainingMilliSeconds){
    return RedisToken.builder()
        .id(jwtString)
        .username(AdminAndMenu)
        .expiration(remainingMilliSeconds/1000)
        .build();
  }
}
