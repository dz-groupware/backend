package com.example.backend.config.redis;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginReqDto {
  @NotNull(message = "아이디를 입력하세요.")
  private String loginId;
  @NotNull(message = "비밀번호를 입력하세요.")
  private String loginPw;
}
