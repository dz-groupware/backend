package com.example.backend.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDto {
  private Long id;
  private Long gnbId;
  private String name;
  private String nameTree;
  private String page;
}
