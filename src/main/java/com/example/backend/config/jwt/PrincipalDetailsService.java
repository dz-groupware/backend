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
  public UserDetails loadUserByUsername(String username){
    PrincipalUserDto principalUser = userMapper.findByLoginId(username);
    if (principalUser == null) {
      throw new UsernameNotFoundException("Employee not found");
    }
    return new PrincipalDetails(principalUser); // PrincipalDetails should implement UserDetails
  }
}
