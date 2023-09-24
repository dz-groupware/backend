package com.example.backend.redis;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.http.Cookie;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationMenuFilter extends OncePerRequestFilter {

  private final String jwtKey;
  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisForMenu;
  private final RedisTemplate<String, PkDto> redisForPayload;
  public AuthorizationMenuFilter(String jwtKey, RedisTemplate<String, String> redisForMenu,
      RedisMapper redisMapper, RedisTemplate<String, PkDto> redisForPayload) {
    this.jwtKey = jwtKey;
    this.redisMapper = redisMapper;
    this.redisForMenu = redisForMenu;
    this.redisForPayload = redisForPayload;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    logger.info("#### AuthorizationMenuFilter ###");
    // 토큰 받아옴
    String accessToken = getAccessTokenFromCookie(request);

    // 유효한 토큰인지 확인 - 토큰 없음
    if (accessToken == null || "".equals(accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    // 유효한 토큰인지 확인 - 유효하지 않음
    String empId = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtKey.getBytes()))
        .build()
        .parseClaimsJws(accessToken)
        .getBody()
        .get("empId", String.class);

    // 레디스 조회 - 사용자 정보
    PkDto pkDto = redisForPayload.opsForValue().get(empId);

    // redis 에 값이 없으면 SQL 조회 후 redis 에 저장
    if (pkDto == null) {
      logger.info("pk is not in redis");
      pkDto = redisMapper.getAllKeys(Long.parseLong(empId));
      // 레디스에 저장
      redisForPayload.opsForValue().set(empId, pkDto);
    }

    // 레디스 조회 - 메뉴 리스트
    Set<String> menuSet = redisForMenu.opsForSet().members(accessToken);

    if (menuSet == null || menuSet.isEmpty()) {
      // 레디스에 값이 없었다면 db 에서 확인 후 저장
      logger.info("jwt is not in redis");
      List<Long> menuList = redisMapper.findMenuId(pkDto.getEmpId(), pkDto.getDeptId(), pkDto.getCompId());
      // 메뉴 리스트
      menuSet = menuList.stream().map(String::valueOf).collect(Collectors.toSet());
      // 레디스에 저장
      redisForMenu.opsForSet().add(accessToken, menuList.stream().map(String::valueOf).toArray(String[]::new));
    }

    // 헤더에서 가져온 접근할 페이지
    String menuId = request.getHeader("menuId");
    logger.info("in menuFilter : "+menuId);
    logger.info("menuId : "+menuId+" || menuSet : "+menuSet);
    // 사용자가 메뉴에 접근할 수 있는지 확인
    if (menuId == null || menuSet.contains(menuId) || menuId.equals("0")) {
      request.setAttribute("pkDto", pkDto);
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
}
