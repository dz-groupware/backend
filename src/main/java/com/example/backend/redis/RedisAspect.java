package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisAspect {

  // redis 조회를 사용하는만큼 예외 발생에 대한 로직 필요
  // 수정과 관련된 메서드에 경우 트랜잭션 필요
  //
  private final RedisTemplate<String, JwtDto> redisForPayload;
  private final RedisMapper redisMapper;
  public RedisAspect(RedisTemplate<String, JwtDto> redisForPayload, RedisMapper redisMapper){
    this.redisForPayload = redisForPayload;
    this.redisMapper = redisMapper;
  }
// test 용으로 쓰던 표현식 RedisService 전체 메서드
//  @Around("execution(* com.example.backend.redis.RedisService.*(..))")
  // MenuService 전체 메서드
  @Around("execution(* com.example.backend.menu.service.MenuService.*(..)) || execution(* com.example.backend.redis.RedisService.*(..)) || execution(* com.example.backend.modal.service.ModalService.*(..))")
  public Object MenuService(ProceedingJoinPoint joinPoint) throws Throwable {
    // 메서드의 파라미터 목록 가져오기
    Object[] args = joinPoint.getArgs();
    JwtDto jwtDto = (JwtDto) args[0];

    // redis 조회
    JwtDto result = redisForPayload.opsForValue().get(jwtDto.getJwt());

    if (result == null) {
      // redis에 값이 없으면 디코딩 하고
      JwtDto info = getInfo(jwtDto);
      args[0] = info;
      // 레디스에 저장
      redisForPayload.opsForValue().set(jwtDto.getJwt(), info);
    } else {
      // 값이 있으면
      args[0] = result;
    }
    return joinPoint.proceed(args);
  }

  private JwtDto getInfo(JwtDto jwtDto) throws JsonProcessingException {
    String resultJWT = new String(Base64.getUrlDecoder().decode(jwtDto.getJwt().split("\\.")[1]));
    return new ObjectMapper().readValue(resultJWT, JwtDto.class);
  }

//
//  @Pointcut()
//  public void pointCut(){}

//  @Before()
//  public void before(){}
//  @After()
//  public void after(){}
//  @After-returning()
//  public void around(){}
//  @After-throwing()
//  public void around(){}

//
//  @Aspect
//  @Component
//  public class MyAopAspect {
//    @Around("execution(* com.example.backend.controller.MyController.*(..))")
//    public Object addJwtDto(ProceedingJoinPoint joinPoint) throws Throwable {
//
//
//
//      // 변경된 파라미터로 원래 메서드 호출
//
//    }
//  }

}
