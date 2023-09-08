package com.example.backend.gnb.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class BasicResponseDto<T> {
  private final Map<String, Object> data = new HashMap<>();

  public BasicResponseDto(T menu, T profile){
    this.data.put("menu", menu);
    this.data.put("profile", profile);
  }
}