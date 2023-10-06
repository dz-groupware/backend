package com.example.backend.common.error;

import com.example.backend.common.error.code.ExceptionCode;
import lombok.Getter;


public class BusinessLogicException extends RuntimeException{
  @Getter
  private final ExceptionCode exceptionCode;

  public BusinessLogicException(ExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.exceptionCode = exceptionCode;
  }
}