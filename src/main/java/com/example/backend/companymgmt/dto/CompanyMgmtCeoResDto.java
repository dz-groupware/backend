package com.example.backend.companymgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyMgmtCeoResDto {
    private Long id;
    private String gender;
    private String loginId;
    private String loginPw;
    private String privEmail;
}
