package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, String> redisForMenu;
  private final RedisTemplate<String, JwtDto> redisForPayload;
  private final RedisMapper redisMapper;

  public RedisService(RedisTemplate<String, String> redisForMenu, RedisMapper redisMapper, RedisTemplate<String, JwtDto> redisForPayload){
    this.redisForMenu = redisForMenu;
    this.redisMapper = redisMapper;
    this.redisForPayload = redisForPayload;
  }

  // redis 1번 db에 데이터 저장
  public JwtDto testRedis(JwtDto jwtDto) {
    System.out.println(jwtDto.getEmpId());
    System.out.println(jwtDto.getCompId());
    return jwtDto;
  }

  // 레디스 db 에 모든 데이터 삭제
  public String flushDb(JwtDto jwtDto, int idx) {
    if (idx == 1) {
      redisForMenu.getConnectionFactory().getConnection().flushDb();
      return "flushDB : idx 1";
    } else if (idx == 2) {
      redisForPayload.getConnectionFactory().getConnection().flushDb();
      return "flushDB : idx 2";
    } else {
      return "There is no database";
    }
  }
}
