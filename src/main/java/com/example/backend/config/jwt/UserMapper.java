package com.example.backend.config.jwt;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  PrincipalUserDto findByLoginId(String loginId);
  PrincipalUserDto getAnotherLogin(Long userId, Long empId);
  void setLastAccess(Long empId);
}
