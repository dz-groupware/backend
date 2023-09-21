package com.example.backend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeptEmployeeDto {
  private Long empId;
  private String empName;
  private Long deptId;
}
