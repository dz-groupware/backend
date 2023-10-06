package com.example.backend.companymgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyMgmtReqDto {

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
  private Long companyId;

  public void setCompanyId(Long id){
    this.companyId=id;
  }


}