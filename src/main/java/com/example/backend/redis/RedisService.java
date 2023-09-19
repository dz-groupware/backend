package com.example.backend.redis;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;
  private final RedisMapper redisMapper;

  public RedisService(RedisTemplate<String, String> redisTemplate, RedisMapper redisMapper){
    this.redisTemplate = redisTemplate;
    this.redisMapper = redisMapper;
  }
  public void saveDataToRedis(String key, List<Long> value) {
//    redisTemplate.opsForValue().set(key, value);
    List<String> stringValueList = value.stream()
        .map(String::valueOf)
        .collect(Collectors.toList());
    redisTemplate.opsForList().leftPushAll(key, stringValueList);
  }
  public String getDataFromRedis(String key){
    return redisTemplate.opsForValue().get(key);
  }

  public List<Long> findMenuId(Long empId, Long deptId, Long compId){
    return redisMapper.findMenuId(empId, deptId, compId);
  }
}
