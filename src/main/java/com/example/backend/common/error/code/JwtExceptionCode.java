package com.example.backend.common.error.code;

import lombok.Getter;

public enum JwtExceptionCode implements ExceptionCode {
  EXPIRED_JWT_TOKEN(402, "JWT 토큰이 만료 되었습니다."),
  NO_REDIS_CONNECTION(500, "레디스 연결이 되지 않았습니다."),
  MISSING_COOKIE(402, "요청된 쿠키가 없습니다."),
  MISSING_TOKENS(402, "요청된 토큰이 없습니다."),
  MISSING_USER_AGENT(400,"유저의 기기 정보가 없습니다."),
  INVALID_JWT_TOKEN(401, "유효하지 않은 JWT 토큰입니다."),
  INVALID_REDIS_TOKEN(401,"레디스에 해당하는 토큰이 없습니다."),
  INVALID_COOKIE(402, "유효하지 않은 쿠키입니다."),
  JSON_PROCESSING_ERROR(500, "JSON 처리 중 에러 발생하였습니다."),
  MISMATCHED_USER_AGENT(401, "유저의 기기 정보가 일치하지 않습니다."),
  TOKEN_NOT_FOUND_IN_REDIS(401, "레디스에 해당하는 토큰이 없습니다."),
  TYPE_MISMATCH(500, "레디스에서 타입과 일치하지 않습니다.");

  @Getter
  private int status;

  @Getter
  private String message;

  JwtExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }
}