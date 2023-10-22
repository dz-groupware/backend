package com.example.backend.menu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RouteDto {
  private Long menuId;
  private Long gnbId;
  private String gnbName;
  private String nameTree;
  private String page;
}