package com.example.backend.config.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public PrincipalDetailsService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override

  public UserDetails loadUserByUsername(String username) {
    System.out.println("여기는 불려?"+ username);
    System.out.println();
    PrincipalUserDto principalUser = userMapper.findByLoginId(username);
    System.out.println("출력되고 있나"+ principalUser.toString());
    if (principalUser == null) {
      throw new UsernameNotFoundException("Employee not found");
    }
    return new PrincipalDetails(principalUser);
  }

  public UserDetails loadUserByUserIdAndEmpId(Long userId, Long empId)  {
    PrincipalUserDto principalUser = userMapper.getAnotherLogin(userId,empId);
    if (principalUser == null) {
      throw new UsernameNotFoundException("Employee not found");
    }
    return new PrincipalDetails(principalUser);
  }
}
