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
    // 로그인이나 테스트 용이면 필터를 건너뛰도록
    String requestURI = request.getRequestURI();
    if(requestURI.startsWith("/api/v1/auth/login") || requestURI.startsWith("/api/v1/test")) {
      logger.info("### AuthorizationMenuFilter : skip ###");
      chain.doFilter(request, response);
      return;
    }

    logger.info("### AuthorizationMenuFilter ###");

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
      pkDto.setMasterYn(redisMapper.checkMaster(pkDto.getEmpId()));
      redisForPayload.opsForValue().set("empId", pkDto);
    }

    String menuId = request.getHeader("menuId");
//    if(menuId == null){
//      logger.info("denied : menuId is null");
//      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//      return;
//    }
    logger.info("in menuFilter : "+menuId);
    logger.info("pkdto......"+pkDto.getEmpId()+pkDto.isMasterYn());
    if(pkDto.isMasterYn()) {
      // 마스터이면 권한 확인 안하고 넘어감
      request.setAttribute("pkDto", pkDto);
      chain.doFilter(request, response);
    } else {
      List<Long> menuList = redisMapper.findMenuId(pkDto.getEmpId(), pkDto.getDeptId(), pkDto.getCompId());
      if (menuId == null|| menuList.contains(Long.parseLong(menuId))|| menuId.equals("0")) {
        request.setAttribute("pkDto", pkDto);
        logger.info(pkDto.getEmpId());
        chain.doFilter(request, response);
      } else {
        logger.info("denied");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      }
    }
  }
}

