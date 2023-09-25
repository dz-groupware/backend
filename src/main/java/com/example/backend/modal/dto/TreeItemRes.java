package com.example.backend.modal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeItemRes {

  private Long id;
  private Long parId;
  private String name;
  private int sortOrder;
  private String type;
  private Long compId;
}
