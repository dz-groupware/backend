package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dto {
  public Long id;
  public String name;
  public String age;

  public void setId(Long id) {
    this.id = id;
  }
}
