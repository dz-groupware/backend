package com.example.backend.user;

import com.example.backend.common.dto.PkDto;
import com.example.backend.config.jwt.PrincipalUserDto;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
  public PrincipalUserDto getAnotherLogin(PkDto pkdto, HttpServletResponse response,Long empId);
}
