//package com.example.backend.redis;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/test")
//public class testRedisController {
//  private final RedisService redisService;
//  public testRedisController(RedisService redisService){
//    this.redisService = redisService;
//
//  }
//
//  @GetMapping("header")
//  public ResponseEntity<?> getFormHeader(){
//    return new ResponseEntity<>(redisService.testRedis(), HttpStatus.OK);
//  }
//
//  @GetMapping("flush")
//  public String flushDB(@RequestParam int idx){
//    return redisService.flushDb(idx);
//  }
//
//}
