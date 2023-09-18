package com.example.backend.config.redis;

import com.example.backend.user.EmpIdRequestDto;
import com.example.backend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserMapper userMapper;
//  private final RedisTemplate<String, String> redisTemplate;
  private final PrincipalDetailsService principalDetailsService;
  @Transactional
  public TokenDto login(LoginReqDto loginReqDto) {
    System.out.println("서비스 로그인dto" + loginReqDto.toString());
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginReqDto.getLoginId(),
              loginReqDto.getLoginPw()
          )
      );

      TokenDto tokenDto = new TokenDto(
          jwtTokenProvider.createAccessToken(authentication),
          jwtTokenProvider.createRefreshToken(authentication)
      );

      return tokenDto;

    }catch(BadCredentialsException e){
      log.error("유효하지않은 로그인아디와 패스워드입니다.");
      throw new Error("유효하지않은 로그인아디와 패스워드입니다.");
    }
  }

  public TokenDto anotherLogin(EmpIdRequestDto empIdRequestDto) {
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
        jwtTokenProvider.createAccessToken(authentication),
        jwtTokenProvider.createRefreshToken(authentication)
    );
    return tokenDto;
  }

  public TokenDto reissueToken(String refreshToken) {

    if(!jwtTokenProvider.validateToken(refreshToken)){
      throw new Error("유효하지 않은 토큰");
    }
    // Access Token 에서 User num을 가져옴
    Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
    // Redis에서 저장된 Refresh Token 값을 가져옴
//    String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName());
//    if(!redisRefreshToken.equals(refreshToken)) {
//      throw new Error("존재하지않은 리프레쉬토큰");
//    }
    // 토큰 재발행
    TokenDto tokenDto = new TokenDto(
        jwtTokenProvider.createAccessToken(authentication),
        jwtTokenProvider.createRefreshToken(authentication)
    );

    return tokenDto;
  }



}