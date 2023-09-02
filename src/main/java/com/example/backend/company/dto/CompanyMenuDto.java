package com.example.backend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyMenuDto {

  private Long id;
  private Long parId;
  private Long compId;
  private String name;
  private Boolean enabledYn;
  private String iconUrl;
  private Integer sortOrder;
  private String nameTree;
  private String idTree;
  private Boolean childNodeYn;
  private Boolean adminYn;
  private Boolean deletedYn;
}
