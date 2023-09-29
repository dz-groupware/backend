package com.example.backend.employeemgmt.dto;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMgmtSignUpReqDto {
    private String name;
    private String empIdNum;
    private String privEmail;
    private String mobileNumber;
}
