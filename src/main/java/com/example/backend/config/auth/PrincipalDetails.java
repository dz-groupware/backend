package com.example.backend.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
    private final EmployeeReqDto employee;

    public PrincipalDetails(EmployeeReqDto employee) {
        this.employee = employee;
    }

    @Override // 해당 member의 권한을 리턴하는곳
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "user");
        return authorities;
    }

    @Override
    public String getUsername() {
        return employee.getLoginId();
    }

    @Override
    public String getPassword() {
        return employee.getLoginPw();
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