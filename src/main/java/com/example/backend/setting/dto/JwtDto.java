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
  private Long compId;
  private Long deptId;

}
