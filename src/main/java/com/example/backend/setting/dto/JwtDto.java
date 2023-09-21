package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {
  private String sub;
  private Long exp;
  private Long userId;
  private Long empId;
  private Long deptId;
  private Long compId;
  private String jwt;
  private boolean masterYn;
  private String id = "";
  private String password = "";

}
