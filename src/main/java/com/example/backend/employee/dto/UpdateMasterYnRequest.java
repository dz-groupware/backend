package com.example.backend.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMasterYnRequest {

  private Long empId;
  private Boolean masterYn;
}
