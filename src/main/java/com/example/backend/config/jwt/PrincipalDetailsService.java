package com.example.backend.config.jwt;

import com.example.backend.user.UserMapper;
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
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    PrincipalUserDto principalUser = userMapper.findByLoginId(username);
    System.out.println("이거호출되나");
    if (principalUser == null) {
      throw new UsernameNotFoundException("Employee not found");
    }
    return new PrincipalDetails(principalUser);
  }

  public UserDetails loadUserByUserIdAndEmpId(Long userId, Long empId) throws UsernameNotFoundException {
    PrincipalUserDto principalUser = userMapper.getAnotherLogin(userId,empId);
    if (principalUser == null) {
      throw new UsernameNotFoundException("Employee not found");
    }
    return new PrincipalDetails(principalUser);
  }
}
