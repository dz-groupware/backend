package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationMenuFilter extends OncePerRequestFilter {

  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisForMenu;
  public AuthorizationMenuFilter(RedisTemplate<String, String> redisForMenu,
      RedisMapper redisMapper) {
    this.redisMapper = redisMapper;
    this.redisForMenu = redisForMenu;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    // 토큰 받아와서
    String accessToken = getAccessTokenFromCookie(request);
    if (accessToken == null || "".equals(accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    // 레디스 조회
    Set<String> result = getListFromRedis(accessToken);
    Set<String> menuSet;
    if (result.isEmpty()) {
      // 레디스에 값이 없었다면 db에서 확인 후 저장
      System.out.println("jwt is not in redis");

      JwtDto jwtDto = getInfo(accessToken);

      List<Long> menuList = redisMapper.findMenuId(jwtDto.getEmpId(), jwtDto.getDeptId(), jwtDto.getCompId());

      // 메뉴 리스트
      menuSet = menuList.stream().map(String::valueOf).collect(Collectors.toSet());

      // 레디스에 저장
      saveDataToRedis(accessToken, menuList.stream().map(String::valueOf).toArray(String[]::new));
    } else {
      System.out.println("jwt is in redis");
      // 레디스에 값이 있었다면 그 값으로 권한이 있는지 확인
      menuSet = result;
    }

    // 헤더에서 가져온 접근할 페이지
    String menuId = request.getHeader("menuId");
    System.out.println("in menuFilter : "+menuId);

    System.out.println("menuId : "+menuId+" || menuSet : "+menuSet);
    // 사용자가 메뉴에 접근할 수 있는지 확인
    if (menuId == null || menuSet.contains(menuId) || menuId.equals("0")) {
      chain.doFilter(request, response);
    } else {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }

  private String getAccessTokenFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("JWT".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  private Set<String> getListFromRedis(String key){
    return redisForMenu.opsForSet().members(key);
//    return redisTemplate.opsForList().range(key, 0, -1);
  }

  public void saveDataToRedis(String key, String[] value) {
    redisForMenu.opsForSet().add(key, value);
  }



  public JwtDto getInfo(String accessToken) throws JsonProcessingException {
    String resultJWT = new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
    return new ObjectMapper().readValue(resultJWT, JwtDto.class);
  }
}
