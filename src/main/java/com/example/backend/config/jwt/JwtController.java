package com.example.backend.config.jwt;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.user.EmpIdRequestDto;
import com.example.backend.user.UserMapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class JwtController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserMapper userMapper;
  private final PrincipalDetailsService principalDetailsService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto,HttpServletRequest request, HttpServletResponse response){
    TokenDto tokenDto = null;
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginReqDto.getLoginId(),
              loginReqDto.getLoginPw()
          )
      );

      tokenDto = new TokenDto(
          jwtTokenProvider.createAccessToken(authentication, request),
          jwtTokenProvider.createRefreshToken(authentication)
      );

    }catch(BadCredentialsException e){
      log.error("유효하지않은 로그인아디와 패스워드입니다.");
      throw new Error("유효하지않은 로그인아디와 패스워드입니다.");
    }

    //리프레쉬만 httpOnly 로넘기기
    Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");

    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);

    return ResponseEntity.accepted().body(new SingleResponseDto<>("accessToken, refreshToken 발급"));
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
    String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
    jwtTokenProvider.validateToken(refreshToken);
    Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken, request);

    TokenDto tokenDto = new TokenDto(
        jwtTokenProvider.createAccessToken(authentication,request),
        jwtTokenProvider.createRefreshToken(authentication)
    );

    Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);
    return ResponseEntity.accepted().body(new SingleResponseDto<>("accessToken, refreshToken 재발급"));
  }

  @PostMapping("/re-login")
  public ResponseEntity<?> anotherLogin(@RequestBody EmpIdRequestDto empIdRequestDto, HttpServletRequest request,HttpServletResponse response) {
    // 직원 인증을 위한 별도의 인증 메서드 호출
    Long userId = SecurityUtil.getUserId();
    Long empId = empIdRequestDto.getEmpId();
    if(userId == null || empId == null) {
      throw new Error("유효하지않은 아이디입니다.");
    }
    UserDetails userDetails = principalDetailsService.loadUserByUserIdAndEmpId(userId, empId);
    System.out.println(userDetails.toString());
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );

    TokenDto tokenDto = new TokenDto(
        jwtTokenProvider.createAccessToken(authentication, request),
        jwtTokenProvider.createRefreshToken(authentication)
    );

    Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);

    // 성공 응답 반환
    return ResponseEntity.accepted().body(new SingleResponseDto<>("accessToken, refreshToken 재발급"));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    String accessToken = jwtTokenProvider.getAccessTokenFromRequest(request);
    String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
    jwtTokenProvider.logout(request);
    Cookie accessTokenCookie = new Cookie("accessToken", null);
    Cookie refreshTokenCookie = new Cookie("refreshToken", null);

    accessTokenCookie.setMaxAge(0); // 만료시간 0으로 설정
    refreshTokenCookie.setMaxAge(0); // 만료시간 0으로 설정

    accessTokenCookie.setPath("/");
    refreshTokenCookie.setPath("/");

    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);

    return ResponseEntity.ok().build();
  }
}