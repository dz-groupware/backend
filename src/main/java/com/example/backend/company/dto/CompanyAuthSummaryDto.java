package com.example.backend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class CompanyAuthSummaryDto {

  private String companyId;
  private String companyName;
  private String authName;
  private String authId;
}
