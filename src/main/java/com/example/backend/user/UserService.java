package com.example.backend.user;

import com.example.backend.config.jwt.PrincipalUserDto;
import com.example.backend.config.jwt.SecurityUtil;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
  public PrincipalUserDto getAnotherLogin( HttpServletResponse response,Long empId);
}
