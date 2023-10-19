package com.example.backend.department.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptListDto {
  private Long id;
  private Long compId;
  private Long parId;
  private String name;
  private String code;
  private String idTree;
  private int sortOrder;
  private boolean managementYn = false;
  private boolean enabledYn = false;
  private boolean includedYn = false;
  private boolean childNodeYn = false;

}
