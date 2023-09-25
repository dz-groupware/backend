package com.example.backend.common.dto;

import lombok.Getter;

@Getter
public class SingleResponseDto<T> {

  private T data;
  public SingleResponseDto(T data) {
    this.data = data;
  }
}
