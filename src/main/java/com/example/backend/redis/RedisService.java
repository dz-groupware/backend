package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;
  private final RedisMapper redisMapper;
  private final JwtToServiceFilter jwtToServiceFilter;

  public RedisService(RedisTemplate<String, String> redisTemplate, RedisMapper redisMapper, JwtToServiceFilter jwtToServiceFilter){
    this.redisTemplate = redisTemplate;
    this.redisMapper = redisMapper;
    this.jwtToServiceFilter = jwtToServiceFilter;
  }

  // jwt 토큰을 key로, 저장할 데이터를 value로 저장
  public void saveDataToRedis(String key, List<Long> value) {
    List<String> stringValueList = value.stream()
        .map(String::valueOf)
        .collect(Collectors.toList());
    redisTemplate.opsForList().leftPushAll(key, stringValueList);
  }
  public String getDataFromRedis(String key){
    return redisTemplate.opsForValue().get(key);
  }

  // key(jwt 토큰)로 데이터 조회
  public List<String> getListFromRedis(String key){
    return redisTemplate.opsForList().range(key, 0, -1);
  }

  // 레디스에 값이 없을 시, 필요한 데이터 조회
  public List<Long> findMenuId(Long empId, Long deptId, Long compId){
    return redisMapper.findMenuId(empId, deptId, compId);
  }



  // 레디스 db 에 모든 데이터 삭제
  public void flushDb(int dbIndex) {
    // 특정 데이터베이스로 전환
//    redisTemplate.getConnectionFactory().getConnection().select(dbIndex);
    // 선택한 데이터베이스의 데이터를 삭제
    redisTemplate.getConnectionFactory().getConnection().flushDb();
  }

  // 토큰 가져오기
  public String getJwt() {
    return jwtToServiceFilter.getJwtToken();
  }

  // 토큰에서 값 가져오기
  public JwtDto getInfo() throws JsonProcessingException {
    String resultJWT = new String(Base64.getUrlDecoder().decode(jwtToServiceFilter.getJwtToken().split("\\.")[1]));
    JwtDto jwtDto = new ObjectMapper().readValue(resultJWT, JwtDto.class);
    return jwtDto;
  }
}
