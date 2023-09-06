package com.example.backend.config.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
  public static Long getUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof PrincipalDetails) {
      return ((PrincipalDetails) auth.getPrincipal()).getUserId();
    }
    return null;
  }

  public static Long getEmployeeId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof PrincipalDetails) {
      return ((PrincipalDetails) auth.getPrincipal()).getEmployeeId();
    }
    return null;
  }

  public static Long getCompanyId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof PrincipalDetails) {
      return ((PrincipalDetails) auth.getPrincipal()).getCompanyId();
    }
    return null;
  }

  public static Long getDepartmentId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof PrincipalDetails) {
      return ((PrincipalDetails) auth.getPrincipal()).getDepartmentId();
    }
    return null;
  }



}