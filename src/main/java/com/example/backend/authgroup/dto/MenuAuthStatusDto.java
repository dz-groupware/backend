package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuAuthStatusDto {

  private Long menuId;
  private Long menuParId;
  private Long compId;
  private String menuName;
  private Long authId;
  private Boolean enabledYn;
  private Boolean childNodeYn;
  private Boolean hasMenu;

}
