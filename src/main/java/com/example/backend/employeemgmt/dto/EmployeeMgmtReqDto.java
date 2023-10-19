package com.example.backend.employeemgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeMgmtReqDto {
    private Long id;
    private String imageUrl;
    private String name;
    private String empIdNum;
    private String gender;
    private Boolean accountYn;
    private String loginId;
    private String loginPw;
    private String privEmail;
    private String mobileNumber;
    private String homeNumber;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date resignationDate;
    private Long departmentId;
    private Long compId;
    private String position;
    private Long deptId;
    private Boolean transferredYn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date edjoinDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date leftDate;
    private Boolean deletedYn;
    private Long empId;

}

