package com.example.backend.companymgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CompanyMgmtReqDto {
    private int id;
    private String code = "";
    private int enabledYn;
    private String name;
    private String abbr;
    private String businessType;
    private String repName;
    private String repIdNum;
    private String repTel;
    private String businessNum;
    private int corpType;
    private String corpNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date establishmentDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date openingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date closingDate;
    private String address;
    private boolean deletedYn;

}