package com.example.backend.redis;

import com.example.backend.config.jwt.PkDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
  private final RedisTemplate<String, String> redisForMenu;
  private final RedisMapper redisMapper;
  public RedisService(RedisTemplate<String, String> redisForMenu, RedisMapper redisMapper){
    this.redisForMenu = redisForMenu;
    this.redisMapper = redisMapper;
  }

  public Set<String> getMenuSetFromRedis (Long empId) {
    return redisForMenu.opsForSet().members(String.valueOf(empId));
  }

  public List<Long> findMenuList (Long empId, Long deptId, Long compId) {
    return redisMapper.findMenuId(empId, deptId, compId);
  }

  public void saveMenuSetToRedis (String key, String value){
    redisForMenu.opsForSet().add(key, value);
  }
}
