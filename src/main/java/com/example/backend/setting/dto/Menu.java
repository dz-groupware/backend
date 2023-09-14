package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu {
  private Long id = 0L;
  private Long parId = 0L;
  private Long compId = 0L;
  private String name = "";
  private String name_tree = "";
  private String id_tree = "";
  private String iconUrl = "0";
  private int sortOrder = 0;
  private int enabledYN = 0;
  private boolean childNodeYn = true;
  private boolean adminYn = true;
  private boolean deletedYn = false;


}
