package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import org.springframework.data.redis.core.RedisTemplate;
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
    // 특정 데이터베이스로 전환
//    redisTemplate.getConnectionFactory().getConnection().select(dbIndex);
    // 선택한 데이터베이스의 데이터를 삭제

  }

//  // 토큰에서 값 가져오기
//  public JwtDto getInfo() throws JsonProcessingException {
//    String resultJWT = new String(
//        Base64.getUrlDecoder().decode(((String)ThreadLocal.withInitial(() -> null).get()).split("\\.")[1]));
//    JwtDto jwtDto = new ObjectMapper().readValue(resultJWT, JwtDto.class);
//    return jwtDto;
//  }
//@Override
//public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {
//    // 레디스 조회해서 있으면 그거 주고
//  Set<String> result = getMenuSetFromRedis(jwt);
//  Set<String> menuSet;
//  JwtDto info;
//  try {
//    info = getInfo(jwt);
//  } catch (JsonProcessingException e) {
//    throw new RuntimeException(e);
//  }
//
//  return new PrincipalJwtDetails(info); // PrincipalDetails should implement UserDetails
//}
//
//  private JwtDto getInfo(String accessToken) throws JsonProcessingException {
//    String resultJWT = new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
//    JwtDto jwtDto = new ObjectMapper().readValue(resultJWT, JwtDto.class);
//    return jwtDto;
//  }
//  private Set<String> getMenuSetFromRedis(String key){
//    return redisTemplate.opsForSet().members(key);
////    return redisTemplate.opsForList().range(key, 0, -1);
//  }
//  private void saveDataToRedis(String key, String[] value) {
//    redisTemplate.opsForSet().add(key, value);
//  }
}
