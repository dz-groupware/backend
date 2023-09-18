package com.example.backend.config.redis;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.user.EmpIdRequestDto;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {
  private final AuthService authService;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto, HttpServletResponse response){
    TokenDto tokenDto = authService.login(loginReqDto);
    Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
    accessTokenCookie.setHttpOnly(true);
    accessTokenCookie.setPath("/");

    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");

    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);

    return new ResponseEntity<>(new SingleResponseDto<>("accessToken, refreshToken 생성"), HttpStatus.ACCEPTED);
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
    String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
    TokenDto tokenDto = authService.reissueToken(refreshToken);
    Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
    accessTokenCookie.setHttpOnly(true);
    accessTokenCookie.setPath("/");

    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");

    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);
    return new ResponseEntity<>(new SingleResponseDto<>("accessToken, refreshToken 재발급"), HttpStatus.ACCEPTED);
  }

  @PostMapping("/re-login")
  public ResponseEntity<?> anotherLogin(@RequestBody EmpIdRequestDto empIdRequestDto, HttpServletResponse response) {
    // 직원 인증을 위한 별도의 인증 메서드 호출
    TokenDto tokenDto = authService.anotherLogin(empIdRequestDto);

    // 토큰을 쿠키에 설정
    Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
    accessTokenCookie.setHttpOnly(true);
    accessTokenCookie.setPath("/");
    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);

    // 성공 응답 반환
    return new ResponseEntity<>(new SingleResponseDto<>("accessToken, refreshToken 재발급"), HttpStatus.ACCEPTED);
  }
}