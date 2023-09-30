package com.example.backend.config.jwt;

import com.example.backend.common.dto.SingleResponseDto;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
  private final PrincipalDetailsService principalDetailsService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto,HttpServletRequest request, HttpServletResponse response){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReqDto.getLoginId(), loginReqDto.getLoginPw()));
    String accessToken = jwtTokenProvider.createAccessToken(authentication, request);
    String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
    jwtTokenProvider.setCookie(response, "accessToken", accessToken);
    jwtTokenProvider.setCookie(response, "refreshToken", refreshToken);

    return ResponseEntity.accepted().body(new SingleResponseDto<>("accessToken, refreshToken 발급"));
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
    String reqRefreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
    jwtTokenProvider.validateToken(reqRefreshToken);
    Authentication authentication = jwtTokenProvider.getAuthentication(reqRefreshToken, request);

    String accessToken = jwtTokenProvider.createAccessToken(authentication, request);
    String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
    jwtTokenProvider.setCookie(response, "accessToken", accessToken);
    jwtTokenProvider.setCookie(response, "refreshToken", refreshToken);
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
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );

    String accessToken = jwtTokenProvider.createAccessToken(authentication, request);
    String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
    jwtTokenProvider.setCookie(response, "accessToken", accessToken);
    jwtTokenProvider.setCookie(response, "refreshToken", refreshToken);
    return ResponseEntity.accepted().body(new SingleResponseDto<>("accessToken, refreshToken 재발급"));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    jwtTokenProvider.logout(request);
    jwtTokenProvider.deleteCookie(response, JwtTokenProvider.ACCESS_TOKEN_NAME);
    jwtTokenProvider.deleteCookie(response, JwtTokenProvider.REFRESH_TOKEN_NAME);
    return ResponseEntity.ok().build();
  }
}