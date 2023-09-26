package com.example.backend.common.error;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException{
  @Getter
  private final JwtExceptionCode exceptionCode;

  public BusinessLogicException(JwtExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.exceptionCode = exceptionCode;
  }
}