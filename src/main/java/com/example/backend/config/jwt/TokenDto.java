package com.example.backend.config.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class TokenDto {
  private String accessToken;
  private String refreshToken;
  private String empId;
  private String compId;

  public TokenDto(String accessToken, String refreshToken, String empId, String compId) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.empId = empId;
    this.compId = compId;
  }
}