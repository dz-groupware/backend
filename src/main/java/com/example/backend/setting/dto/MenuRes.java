package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuRes {

  private Long id = 0L;
  private Long parId = 0L;
  private Long compId = 0L;
  private String name = "";
  private String iconUrl = "0";
  private int sortOrder = 0;
  private int enabledYN = 0;
}
