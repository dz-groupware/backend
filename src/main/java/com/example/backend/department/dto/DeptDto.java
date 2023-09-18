package com.example.backend.department.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptDto {
  private Long id = 0L;
  private Long compId = 0L;
  private Long parId = 0L;
  private String name = "";
  private String parName = "";
  private String code = "";
  private String abbr = "";
  private boolean enabledYn = true;
  private boolean includedYn = true;
  private boolean managementYn = false;
  private int sortOrder = 0;
  private String idTree = "";
  private String nameTree = "";
  private boolean childNodeYn = true;
  private boolean deletedYn = false;
  private String status = "";
}

