package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyMenuDto {

  private Long menuId;
  private Long menuParId;
  private Long compId;
  private String menuName;
  private Boolean enabledYn;
  private Integer sortOrder;
  private String idTree;
  private String nameTree;
  private Boolean childNodeYn;
  private Boolean adminYn;
  private Boolean deletedYn;
}
