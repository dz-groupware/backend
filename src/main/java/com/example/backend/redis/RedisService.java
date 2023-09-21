package com.example.backend.redis;

import com.example.backend.config.jwt.PrincipalDetails;
import com.example.backend.config.jwt.PrincipalUserDto;
import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RedisService implements UserDetailsService {

  private final RedisTemplate<String, String> redisTemplate;
  private final RedisMapper redisMapper;

  public RedisService(RedisTemplate<String, String> redisTemplate, RedisMapper redisMapper){
    this.redisTemplate = redisTemplate;
    this.redisMapper = redisMapper;
//    this.authorizationFilterWithJWT = authorizationFilterWithJWT;
  }
  // 레디스 db 에 모든 데이터 삭제
  public void flushDb(int dbIndex) {
    // 특정 데이터베이스로 전환
//    redisTemplate.getConnectionFactory().getConnection().select(dbIndex);
    // 선택한 데이터베이스의 데이터를 삭제
    redisTemplate.getConnectionFactory().getConnection().flushDb();
  }

//  // 토큰에서 값 가져오기
//  public JwtDto getInfo() throws JsonProcessingException {
//    String resultJWT = new String(
//        Base64.getUrlDecoder().decode(((String)ThreadLocal.withInitial(() -> null).get()).split("\\.")[1]));
//    JwtDto jwtDto = new ObjectMapper().readValue(resultJWT, JwtDto.class);
//    return jwtDto;
//  }
@Override
public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {
    // 레디스 조회해서 있으면 그거 주고
  Set<String> result = getMenuSetFromRedis(jwt);
  Set<String> menuSet;
  JwtDto info;
  try {
    info = getInfo(jwt);
  } catch (JsonProcessingException e) {
    throw new RuntimeException(e);
  }

  return new PrincipalJwtDetails(info); // PrincipalDetails should implement UserDetails
}

  private JwtDto getInfo(String accessToken) throws JsonProcessingException {
    String resultJWT = new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
    JwtDto jwtDto = new ObjectMapper().readValue(resultJWT, JwtDto.class);
    return jwtDto;
  }
  private Set<String> getMenuSetFromRedis(String key){
    return redisTemplate.opsForSet().members(key);
//    return redisTemplate.opsForList().range(key, 0, -1);
  }
  private void saveDataToRedis(String key, String[] value) {
    redisTemplate.opsForSet().add(key, value);
  }
}
