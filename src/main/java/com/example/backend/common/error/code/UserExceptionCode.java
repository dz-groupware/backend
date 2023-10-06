package com.example.backend.common.error.code;

import lombok.Getter;

public enum UserExceptionCode implements ExceptionCode{
  UnchangeableSelfAuthority(404, "자신의 권한은 바꿀 수 없습니다.");


  @Getter
  private int status;

  @Getter
  private String message;

  UserExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }


}
