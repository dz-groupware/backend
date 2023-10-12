package com.example.backend.companymgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CompanyMgmtTreeListResDto {

    private Long id;
    private Long parId;
    private String nameTree;

}
