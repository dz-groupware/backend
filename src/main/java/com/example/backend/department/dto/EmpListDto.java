package com.example.backend.department.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpListDto {
  private String deptName;
  private String position;
  private String empName;
  private int sortOrder;
}
