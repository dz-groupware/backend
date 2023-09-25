package com.example.backend.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeCompanyDto {
  private Long employeeId;
  private Long companyId;
}
