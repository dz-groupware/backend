package com.example.backend.authgroup.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddEmpAuthDto {
  private Long employeeId;
  private Map<Long, Boolean> selectedAuthIds = new HashMap<>();
}
