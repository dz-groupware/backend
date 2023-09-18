package com.example.backend.user;

import com.example.backend.config.redis.PrincipalUserDto;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
  public PrincipalUserDto getAnotherLogin( HttpServletResponse response,Long empId);
}
