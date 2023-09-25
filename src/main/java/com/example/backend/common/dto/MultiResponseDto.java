package com.example.backend.common.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MultiResponseDto<T> {

  private List<T> data;
  private PageDto pageInfo;

  public MultiResponseDto(List<T> data, PageDto pageInfo) {
    this.data = data;
    this.pageInfo = pageInfo;
  }
}
