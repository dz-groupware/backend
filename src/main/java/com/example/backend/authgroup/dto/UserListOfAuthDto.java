package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserListOfAuthDto {

  private Long id;
  private String deptName;
  private String empPosition;
  private String empName;
}
