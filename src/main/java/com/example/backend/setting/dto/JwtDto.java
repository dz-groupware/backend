package com.example.backend.setting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {
  private String sub;
  private Long exp;
  private Long userId;
  private Long empId;
  private Long deptId;
  private Long compId;
  private String jwt;
  private boolean masterYn;
  private String id = "";
  private String password = "";

  public JwtDto(String jwt){
    this.jwt = jwt;
  }

  @JsonCreator
  public JwtDto(@JsonProperty("sub") String sub, @JsonProperty("exp") Long exp, @JsonProperty("empId") Long empId, @JsonProperty("compId") Long compId, @JsonProperty("deptId") Long deptId, @JsonProperty("userId") Long userId ){
    this.sub = sub;
    this.exp = exp;
    this.userId = userId;
    this.empId = empId;
    this.deptId = deptId;
    this.compId = compId;
  }
}
