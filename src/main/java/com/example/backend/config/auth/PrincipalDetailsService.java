package com.example.backend.config.auth;

import com.example.backend.employee.mapper.EmployeeMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public PrincipalDetailsService(EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeReqDto employee = employeeMapper.findByLoginId(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Employee not found");
        }
        return new PrincipalDetails(employee); // PrincipalDetails should implement UserDetails
    }
}
