package com.example.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginExceptionCode {
  UNMATCHED_PASSWORD(401, "비밀번호가 일치하지 않습니다."),
  ID_NOT_FOUND(401, "아이디가 없습니다.");

  private final int status;
  private final String message;
}