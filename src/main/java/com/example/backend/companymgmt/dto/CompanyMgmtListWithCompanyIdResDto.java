package com.example.backend.companymgmt.dto;

import lombok.Getter;

@Getter
public class CompanyMgmtListWithCompanyIdResDto extends CompanyMgmtListResDto{

    private Long companyId;

    public CompanyMgmtListWithCompanyIdResDto(CompanyMgmtListResDto data, Long companyId) {
        super(data.getId(), data.getCode(), data.getName(), data.getCorpType(), data.getRepName());
        this.companyId=companyId;
    }

}
