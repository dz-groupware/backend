package com.example.backend.common.error;

import com.example.backend.common.error.code.JwtExceptionCode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
  public static final String ACCESS_TOKEN_NAME = "accessToken";

  @ExceptionHandler
  public ResponseEntity handleBusinessLogicException(BusinessLogicException e, HttpServletResponse response) {
    if (e.getExceptionCode() instanceof JwtExceptionCode) {
      JwtExceptionCode jwtException = (JwtExceptionCode) e.getExceptionCode();
      if (jwtException.getStatus() == 402) {
        // 402 상태 코드에 해당하는 에러의 경우, 쿠키 삭제 로직 수행
        log.info("쿠키지우는 과정이 일어나고있습니다.");
        Cookie cookie = new Cookie("yourCookieName", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
      }
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
