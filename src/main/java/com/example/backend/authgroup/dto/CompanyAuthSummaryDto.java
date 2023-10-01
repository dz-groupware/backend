package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class CompanyAuthSummaryDto {

  private Long id;
  private Long companyId;
  private Long authId;
  private String authName;
  private String companyName;

}
