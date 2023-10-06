package com.example.backend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CompanyWithEmployeeCountDto {
  private Long companyId;
  private Boolean enabledYn;
  private String companyName;
  private Boolean childNodeYn;
  private Integer employeeCount;
  private Boolean hasDepartment;

}