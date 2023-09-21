package com.example.backend.redis;

import com.example.backend.setting.dto.JwtDto;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
public class PrincipalJwtDetails implements UserDetails {

  private final JwtDto jwtDto;

  public PrincipalJwtDetails(JwtDto jwtDto) {
    this.jwtDto = jwtDto;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(() -> "user");
    return authorities;
  }

  public String getSub() {
    return jwtDto.getSub();
  }
  public Long getExp() {
    return jwtDto.getExp();
  }
  public Long getUserId() {
    return jwtDto.getUserId();
  }
  public Long getEmpId() {
    return jwtDto.getEmpId();
  }
  public Long getDeptId() {
    return jwtDto.getDeptId();
  }
  public Long getCompanyId() {
    return jwtDto.getCompId();
  }
  public String getJwt(){return jwtDto.getJwt();}
  public boolean isMasterYn(){return jwtDto.isMasterYn();}
  @Override
  public String getUsername() {
    return jwtDto.getId();
  }

  @Override
  public String getPassword() {
    return jwtDto.getPassword();
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