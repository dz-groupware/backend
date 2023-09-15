package com.example.backend.department.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DeptRes {
  private Long id;
  private Long compId;
  private Long parId;
  private String name;
  private String code;
  private String abbr;
  private boolean enabledYn;
  private boolean includedYn;
  private boolean managementYn;
  private int sortOrder;
  private String idTree;
  private String nameTree;
  private boolean childNodeYn;
  private boolean deletedYn;
}
