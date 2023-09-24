package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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

  @GetMapping("header")
  public ResponseEntity<?> getFormHeader(@RequestAttribute PkDto pkDto){
    return new ResponseEntity<>(redisService.testRedis(pkDto), HttpStatus.OK);
  }

  @GetMapping("flush")
  public String flushDB(@CookieValue("JWT") String jwt, @RequestParam int idx){
    return redisService.flushDb(new JwtDto(jwt), idx);
  }

}
