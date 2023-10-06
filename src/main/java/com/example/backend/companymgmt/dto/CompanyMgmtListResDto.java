package com.example.backend.companymgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyMgmtListResDto {

    private Long id;
    private String code;
    private String name;
    private Integer corpType;
    private String repName;
}
