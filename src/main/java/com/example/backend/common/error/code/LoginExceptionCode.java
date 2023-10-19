package com.example.backend.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginExceptionCode implements ExceptionCode {
  UNMATCHED_PASSWORD(405, "비밀번호가 일치하지 않습니다."),
  ID_NOT_FOUND(405, "해당하는 아이디가 없습니다."),
  DISABLED(405, "사용할 수 없는 계정입니다."),
  ACCOUNT_EXPIRED(405,"만료된 계정입니다.");

  private final int status;
  private final String message;
}