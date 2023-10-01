package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeAuthStatusDto {
  private Long id;
  private Long companyId;
  private Long authId;
  private String authName;
  private String companyName;
  private Boolean enabledYn;
  private Boolean hasAuth;
}
