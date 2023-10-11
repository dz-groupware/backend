package com.example.backend.companymgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CompanyMgmtCheckSignUpResDto {
    private List<CompanyMgmtCeoResDto> data;
    private boolean isFromCheck;
}
