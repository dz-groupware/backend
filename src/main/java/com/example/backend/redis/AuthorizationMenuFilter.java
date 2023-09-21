package com.example.backend.redis;

import com.example.backend.menu.service.MenuServiceImpl;
import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationMenuFilter extends OncePerRequestFilter {

  private final String jwtKey;
  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisTemplate;
  public AuthorizationMenuFilter(String jwtKey, RedisTemplate<String, String> redisTemplate,
      RedisMapper redisMapper) {
    this.jwtKey = jwtKey;
    this.redisMapper = redisMapper;
    this.redisTemplate = redisTemplate;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    // 사용자 정보 생성
    JwtDto info = (JwtDto) request.getAttribute("JWT");

    // 마스터 여부 dto에 저장
    info.setMasterYn(redisMapper.checkMaster(info.getEmpId()));
//
//    User principal = new User("", "", AuthorityUtils.createAuthorityList("")); // 예시 사용자 정보와 권한
//    Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null);
//    ((UsernamePasswordAuthenticationToken) authentication).setDetails(info);
//    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 토큰 가져오기
    String accessToken = info.getJwt();

    // 레디스 조회
    Set<String> result = getListFromRedis(accessToken);
    Set<String> menuSet;
    System.out.println("result.size : " + result.size());
    if (result.size() == 0) {
      System.out.println("jwt is not in redis");
      // 레디스에 값이 없었다면 db에서 확인
      // jwt 토큰을 열어서 그 안에 있는 값으로 메뉴 리스트 조회

      // 메뉴 리스트
      menuSet = redisMapper.findMenuId(info.getEmpId(), info.getDeptId(), info.getCompId()) // String 배열로 변환
          .stream().map(String::valueOf).collect(Collectors.toSet());

      // 레디스에 저장
      String[] saveMenuSet = menuSet.stream().map(String::valueOf).toArray(String[]::new);
      saveDataToRedis(accessToken, saveMenuSet);
    } else {
      System.out.println("jwt is in redis");
      // 레디스에 값이 있었다면 그 값으로 권한이 있는지 확인
      menuSet = result;
    }

    // 헤더에서 가져온 접근할 페이지
    String menuId = request.getHeader("menuId");

    // 사용자가 메뉴에 접근할 수 있는지 확인
    if (menuSet.contains(menuId)) {
      chain.doFilter(request, response);
    } else {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }

  private Set<String> getListFromRedis(String key){
    return redisTemplate.opsForSet().members(key);
//    return redisTemplate.opsForList().range(key, 0, -1);
  }



  public void saveDataToRedis(String key, String[] value) {
    redisTemplate.opsForSet().add(key, value);
  }
}
