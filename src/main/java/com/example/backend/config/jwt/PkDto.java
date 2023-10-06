package com.example.backend.config.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PkDto {
  private Long userId;
  private Long empId;
  private Long deptId;
  private Long compId;
  private boolean masterYn;
}
