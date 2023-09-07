package com.example.backend.modal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRes {
  private Long compId;
  private String compName;
  private Long deptId;
  private String deptName;
  private Long empId;
  private String empName;
  private String lastIp;
  private String lastAccess;
  private String position;
  private String imageUrl;
}
