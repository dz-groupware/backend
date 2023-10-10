package com.example.backend.config.jwt;

import com.example.backend.common.error.BusinessLogicException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
public class FilterChainExceptionHandler extends OncePerRequestFilter {
  private final HandlerExceptionResolver resolver; // HandlerExceptionResolver를 빈으로 주입 받는다.

  public FilterChainExceptionHandler(
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // 다음 필터를 호출하기 전에 doFilter를 try/catch문으로 감싼다.
    try {
      filterChain.doFilter(request, response);
    } catch (BusinessLogicException e) {
      log.error("Spring Security Filter Chain Exception:", e);
      System.out.println("여기가 떠야하는데 ");
      resolver.resolveException(request, response, null, e);
    }
  }
}