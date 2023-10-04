package com.example.backend.menu.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuRes {
  private Long id = 0L;
  private Long parId = 0L;
  private Long compId = 0L;
  private String name = "";
  private String parName = "";
  private String nameTree = "";
  private String idTree = "";
  private String iconUrl = "https://dz-test-image.s3.ap-northeast-2.amazonaws.com/icon/default.png";
  private int sortOrder = 0;
  private boolean enabledYn = true;
  private boolean childNodeYn = true;
  private boolean adminYn = false;
  private boolean deletedYn = false;
  private Long pageId = 1L;
}
