package com.example.backend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DepartmentWithEmployeeCountDto {
  private Long departmentId;
  private Boolean enabledYn;
  private String departmentName;
  private Boolean childNodeYn;
  private Integer employeeCount;
}
