package com.example.backend.user;

import com.example.backend.config.jwt.PrincipalDetails;
import com.example.backend.config.jwt.PrincipalUserDto;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.config.jwt.TokenService;
import com.example.backend.setting.dto.JwtDto;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final TokenService tokenService;

  public UserServiceImpl(UserMapper userMapper,
      TokenService tokenService) {
    this.userMapper = userMapper;
    this.tokenService = tokenService;
  }

  public PrincipalUserDto getAnotherLogin(HttpServletResponse response, Long empId) {
//    Long userId = SecurityUtil.getUserId();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtDto jwtDto = (JwtDto) authentication.getDetails();
    Long userId = jwtDto.getUserId();
    if(userId == null || empId == null) {
      throw new Error("유효하지않은 아이디입니다.");
    }
    PrincipalUserDto userDto = userMapper.getAnotherLogin(userId, empId);
    PrincipalDetails principalDetails = new PrincipalDetails(userDto);
    String jwtToken = tokenService.createToken(principalDetails);
    Cookie jwtCookie = tokenService.createJwtCookie(jwtToken);
    response.addCookie(jwtCookie);
    return userDto;
  }
}
