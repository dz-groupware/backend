package com.example.backend.authgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateAuthDto {
  private Long id;
  private String authName;
  private boolean enabledYn;
}
