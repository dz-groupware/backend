package com.example.backend.gnb.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class BasicResponseDto<T> {
  private final Map<String, Object> data = new HashMap<>();

  public BasicResponseDto(T menu, T favor, T profile, T empId, T compId){
    this.data.put("menu", menu);
    this.data.put("favor", favor);
    this.data.put("profile", profile);
    this.data.put("empId", empId);
    this.data.put("compId", compId);
  }
}