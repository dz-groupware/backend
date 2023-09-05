package com.example.backend.employee.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResDto {

  private Long id;
  private Long userId;
  private String empName;
  private String gender;
  private String loginId;
  private String loginPw;
  private String imageUrl;
  private String accountYn;
  private LocalDateTime lastAccess;
  private String email;
  private String mobileNumber;
  private String homeNumber;
  private String position;
  private String address;
  private String compName;


  public LocalDateTime getLastAccess() {
    if (lastAccess == null) {
      updateLastAccess(LocalDateTime.now());
    }
    return lastAccess;
  }

  private void updateLastAccess(LocalDateTime lastAccess) {
    this.lastAccess = lastAccess;
  }
}