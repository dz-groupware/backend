package com.example.backend.user;

import com.example.backend.config.jwt.PrincipalUserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  PrincipalUserDto findByLoginId(String loginId);
  PrincipalUserDto getAnotherLogin(Long userId, Long empId);
}



