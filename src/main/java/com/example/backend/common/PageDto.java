package com.example.backend.common;

import lombok.Getter;

@Getter
public class PageDto {

  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private long totalPages;

  public PageDto(int pageNumber, int pageSize, long totalElements) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
    this.totalPages = (totalElements + pageSize - 1) / pageSize;
  }
}
