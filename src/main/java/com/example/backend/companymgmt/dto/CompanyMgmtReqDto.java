package com.example.backend.companymgmt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
  private String privEmail;
  private String repTel;
  private String gender;
  private String loginId;
  private String loginPw;
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
  private Long employeeId;

}