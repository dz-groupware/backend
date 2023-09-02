package com.example.backend.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page<T> {

  private List<T> data;
  private PageDto pageInfo;

}
