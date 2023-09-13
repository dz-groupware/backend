package com.example.backend.companymgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class CompanyMgmtResDto {

  private Long id;
  private Long parId;
  private String code;
  private Boolean enabledYn;
  private String name;
  private String abbr;
  private String businessType;
  private String repName;
  private String repIdNum;
  private String repTel;
  private String businessNum;
  private Integer corpType;
  private String corpNum;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date establishmentDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date openingDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date closingDate;
  private String address;
  private Boolean deletedYn;

}