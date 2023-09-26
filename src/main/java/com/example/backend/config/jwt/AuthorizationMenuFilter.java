package com.example.backend.config.jwt;

import com.example.backend.redis.RedisMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationMenuFilter extends OncePerRequestFilter {

  private final String jwtKey;
  private final RedisMapper redisMapper;
  private final RedisTemplate<String, String> redisForMenu;
  private final RedisTemplate<String, PkDto> redisForPayload;
  private final JwtTokenProvider tokenProvider;
  public AuthorizationMenuFilter(String jwtKey, RedisTemplate<String, String> redisForMenu,
      RedisMapper redisMapper, RedisTemplate<String, PkDto> redisForPayload, JwtTokenProvider tokenProvider) {
    this.jwtKey = jwtKey;
    this.redisMapper = redisMapper;
    this.redisForMenu = redisForMenu;
    this.redisForPayload = redisForPayload;
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    logger.info("### AuthorizationMenuFilter ###");

    if ("/api/v1/auth/login".equals(request.getRequestURI())) {
      chain.doFilter(request, response);
      return;
    }

    String accessToken = tokenProvider.getAccessTokenFromRequest(request);

    if (accessToken == null || "".equals(accessToken)) {
      response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
      return;
    }

    Long empId = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtKey.getBytes()))
        .build()
        .parseClaimsJws(accessToken)
        .getBody()
        .get("empId", Long.class);

    PkDto pkDto = redisForPayload.opsForValue().get(String.valueOf(empId));

    if (pkDto == null) {
      logger.info("pk is not in redis");
      pkDto = redisMapper.getAllKeys(empId);
      redisForPayload.opsForValue().set("empId", pkDto);
    }

    List<Long> menuList = redisMapper.findMenuId(pkDto.getEmpId(), pkDto.getDeptId(), pkDto.getCompId());

    String menuId = request.getHeader("menuId");

    logger.info("in menuFilter : "+menuId);

    // menuId == null 지울예정
    if (menuId == null || menuList.contains(Long.parseLong(menuId))|| menuId.equals("0")) {
      request.setAttribute("pkDto", pkDto);
      chain.doFilter(request, response);
    } else {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }
}
