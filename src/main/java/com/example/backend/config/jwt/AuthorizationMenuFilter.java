package com.example.backend.config.jwt;

import com.example.backend.redis.RedisMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationMenuFilter extends OncePerRequestFilter {

  private final RedisMapper redisMapper;
  public AuthorizationMenuFilter(RedisMapper redisMapper) {
    this.redisMapper = redisMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    String menuId = request.getHeader("menuId");
    logger.info("in menuFilter : "+menuId);

    if(Boolean.TRUE.equals(SecurityUtil.getMasterYn())) {

      // 마스터이면 권한 확인 안하고 넘어감
      chain.doFilter(request, response);
    } else {
      List<Long> menuList = redisMapper.findMenuId(SecurityUtil.getEmployeeId(), SecurityUtil.getDepartmentId(), SecurityUtil.getCompanyId());
      if (menuId == null|| menuList.contains(Long.parseLong(menuId))|| menuId.equals("0")) {
        chain.doFilter(request, response);
      } else {
        logger.info("denied");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      }
    }
  }
}

