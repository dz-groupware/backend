package com.example.backend.employeemgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EmployeeMgmtReqDto {
    private Long id;
    private String imageUrl;
    private String name;
    private String empIdNum;
    private String gender;
    private Boolean accountYn;
    private String loginId;
    private String loginPw;
    private String email;
    private String privEmail;
    private String mobileNumber;
    private String homeNumber;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date resignationDate;
    public void setId(Long id) {
        this.id=id;
    }
}

