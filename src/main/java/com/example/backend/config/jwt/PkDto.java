package com.example.backend.config.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PkDto {
  private Long userId;
  private Long empId;
  private Long deptId;
  private Long compId;
  private boolean masterYn;
}
