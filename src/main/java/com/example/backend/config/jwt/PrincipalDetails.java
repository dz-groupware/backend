package com.example.backend.config.jwt;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@ToString
public class PrincipalDetails implements UserDetails {

  private final PrincipalUserDto principalUser;

  public PrincipalDetails(PrincipalUserDto employee) {
    this.principalUser = employee;
  }

  @Override // 해당 member의 권한을 리턴하는곳
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(() -> "user");
    return authorities;
  }

  @Override
  public String getUsername() {
    return principalUser.getLoginId();
  }

  public Long getUserId() {
    return principalUser.getUserId();
  }

  public Long getEmployeeId() {
    return principalUser.getEmpId();
  }

  public Long getCompanyId() {
    return principalUser.getCompId();
  }

  public Long getDepartmentId(){
    return principalUser.getDeptId();
  }

  public Boolean getMasterYn() {
    return principalUser.getMasterYn();
  }
  @Override
  public String getPassword() {
    return principalUser.getLoginPw();
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}