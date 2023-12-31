package com.example.backend.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PrincipalUserDto {

    private Long userId;
    private String userName;
    private String loginId;
    private String loginPw;
    private Long empId;
    private Long compId;
    private Long deptId;
    private Boolean masterYn;
}