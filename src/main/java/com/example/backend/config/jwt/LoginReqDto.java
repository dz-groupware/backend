package com.example.backend.config.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginReqDto {

  private String loginId;
  private String loginPw;
}
