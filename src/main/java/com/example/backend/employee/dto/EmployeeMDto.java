package com.example.backend.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeMDto {
  private Long empId;
  private Long compId;
  private String empName;
  private String empPosition;
  private Boolean empMasterYn;
}
