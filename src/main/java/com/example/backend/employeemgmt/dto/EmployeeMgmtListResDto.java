package com.example.backend.employeemgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class EmployeeMgmtListResDto {
    private Long id;
    private String imageUrl;
    private String userId;
    private String loginId;
    private String name;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinDate;
}
