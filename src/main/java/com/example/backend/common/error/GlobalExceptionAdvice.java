package com.example.backend.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

  @ExceptionHandler
  public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
    final ErrorResponse response = ErrorResponse.of(HttpStatus.valueOf(e.getExceptionCode().getStatus()), e.getExceptionCode().getMessage());
    return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
  }
}
