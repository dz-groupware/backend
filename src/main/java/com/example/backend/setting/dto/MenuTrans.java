package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuTrans {
  private Long id = 0L;
  private Long parId = 0L;
  private String name = "";
  private String idTree = "";
  private String nameTree = "";

}
