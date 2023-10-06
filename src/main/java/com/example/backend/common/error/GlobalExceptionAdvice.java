package com.example.backend.common.error;

import com.example.backend.common.error.code.JwtExceptionCode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
  public static final String ACCESS_TOKEN_NAME = "accessToken";

  @ExceptionHandler
  public ResponseEntity handleBusinessLogicException(BusinessLogicException e, HttpServletResponse httpResponse) {
    if(e.getExceptionCode() == JwtExceptionCode.EXPIRED_JWT_TOKEN) {
      deleteCookie(httpResponse, ACCESS_TOKEN_NAME);
    }
    final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.valueOf(e.getExceptionCode().getStatus()), e.getExceptionCode().getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
  }


  private void deleteCookie(HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(0);  // 쿠키 만료
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
