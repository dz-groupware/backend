package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testRedisController {
  private final RedisService redisService;
  public testRedisController(RedisService redisService){
    this.redisService = redisService;
  }

  // header에서 원하는 값 가져오기
  // 쿠키 읽기
  // jwt, empId, menuId 가 필요함
  // 우선 jwt만

  @GetMapping("header")
  public ResponseEntity<?> getFormHeader(@CookieValue("JWT") String jwt){
    return new ResponseEntity<>(redisService.testRedis(new JwtDto(jwt)), HttpStatus.OK);
  }
  @GetMapping("flush")
  public String flushDB(@CookieValue("JWT") String jwt, @RequestParam int idx){
    return redisService.flushDb(new JwtDto(jwt), idx);
  }

}
