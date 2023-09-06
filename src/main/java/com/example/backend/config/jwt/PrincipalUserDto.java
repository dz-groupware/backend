package com.example.backend.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrincipalUserDto {

    private Long userId;
    private String userName;
    private String loginId;
    private String loginPw;
    private Long empId;
    private Long compId;
    private Long deptId;
}