package com.example.backend.redis;

import com.example.backend.config.jwt.PkDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, String> redisForMenu;
  private final RedisTemplate<String, PkDto> redisForPayload;
  private final RedisMapper redisMapper;

  public RedisService(RedisTemplate<String, String> redisForMenu, RedisMapper redisMapper, RedisTemplate<String, PkDto> redisForPayload){
    this.redisForMenu = redisForMenu;
    this.redisMapper = redisMapper;
    this.redisForPayload = redisForPayload;
  }

  // 사용자 정보를 제대로 가져오는지 확인할 때
  public PkDto testRedis(PkDto pkDto) {
    return pkDto;
  }

  // 레디스 db 에 모든 데이터 삭제
  public String flushDb(int idx) {
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

  // 특정 key로 찾아서 삭제
  public void deletePk(String key){
    redisForPayload.delete(key);
  }

  // 수정 없음.


}
