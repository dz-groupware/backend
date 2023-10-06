package com.example.backend.employeemgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EmployeeMgmtCheckSignUpResultResDto {
    private List<EmployeeMgmtListResDto> data;
    private boolean isFromCheck;
}
