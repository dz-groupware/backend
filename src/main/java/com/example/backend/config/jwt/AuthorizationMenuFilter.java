package com.example.backend.config.jwt;

import com.example.backend.redis.RedisService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationMenuFilter extends OncePerRequestFilter {
  private final RedisService redisService;
  public AuthorizationMenuFilter(RedisService redisService) {
    this.redisService = redisService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    String requestURI = request.getRequestURI();
    if(requestURI.startsWith("/api/v1/auth/login") || requestURI.startsWith("/api/v1/auth/logout")) {
      logger.info("### AuthorizationMenuFilter : skip ### URI : " + requestURI);
      chain.doFilter(request, response);
      return;
    }

    if(Boolean.TRUE.equals(SecurityUtil.getMasterYn())) {
      // 마스터이면 권한 확인 안하고 넘어감
      logger.info("### AuthorizationMenuFilter : master ###");
      chain.doFilter(request, response);
    } else {
      String menuId = request.getHeader("menuId");
      logger.info("### AuthorizationMenuFilter : " + menuId + " ###");
      Set<String> result = redisService.getMenuSetFromRedis(SecurityUtil.getEmployeeId());
      if (result.size() < 1) {
        // SQL 에서 menuList 조회
        List<Long> menuList = redisService.findMenuList(SecurityUtil.getEmployeeId(), SecurityUtil.getDepartmentId(), SecurityUtil.getCompanyId());
        // NoSql 에 menuSet 저장
        if (menuList.size() == 0) {
          logger.info("menuList is not null. but size is 0");
          menuList.add(0L);
        }
        logger.info("result is null");
        result = menuList.stream().map(Object::toString).collect(Collectors.toSet());
        redisService.saveMenuSetToRedis(String.valueOf(SecurityUtil.getEmployeeId()), result.toArray(new String[result.size()]));
      }
      if (Objects.equals(menuId, "0")) {
        chain.doFilter(request, response);
      } else if (menuId != null && result.contains(menuId)) {
        chain.doFilter(request, response);
      }
      else {
        logger.info("denied");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      }
    }
  }
}

