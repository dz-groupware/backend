package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAuthDto {
  private Long id;
  private String authName;
  private boolean enabledYn;
}
