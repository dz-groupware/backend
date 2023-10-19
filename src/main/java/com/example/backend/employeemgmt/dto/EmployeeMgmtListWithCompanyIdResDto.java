package com.example.backend.employeemgmt.dto;


import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
public class EmployeeMgmtListWithCompanyIdResDto extends EmployeeMgmtListResDto{

    private Long companyId;


    public EmployeeMgmtListWithCompanyIdResDto(EmployeeMgmtListResDto data, Long companyId) {
        super(data.getId(), data.getImageUrl(), data.getLoginId(), data.getName(), data.getJoinDate());
        this.companyId=companyId;

    }
}
