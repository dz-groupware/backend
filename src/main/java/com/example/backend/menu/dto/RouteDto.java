package com.example.backend.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDto {
  private Long menuId;
  private Long gnbId;
  private String gnbName;
  private String nameTree;
  private String page;
}