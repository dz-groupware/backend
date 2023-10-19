package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.*;
import com.example.backend.employeemgmt.dto.EmployeeMgmtCheckSignUpResultResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtSignUpReqDto;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface CompanyMgmtService {

  List<CompanyMgmtListResDto> getOpenedCompanyMgmtList();

  List<CompanyMgmtListResDto>  getClosedCompanyMgmtList();
    List<CompanyMgmtListWithCompanyIdResDto> getCompanyMgmtList();


  @Transactional
  CompanyMgmtCheckSignUpResDto checkSignUp(CompanyMgmtSignUpReqDto companymgmt);

  CompanyMgmtResDto getCompanyDetailsById(Long id);

  List<CompanyMgmtListResDto> findCompanyMgmtList(String name, int enabledType);

  void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);
  void removeCompanyMgmt(Long id);


  void modifyCompanyMgmt(CompanyMgmtReqDto companyMgmt);


  List<CompanyMgmtTreeListResDto> getCompanyMgmtNameTreeList();

  List<CompanyMgmtListResDto> findOpenCompanyMgmtList(String name, int enabledType);

  List<CompanyMgmtListResDto> findCloseCompanyMgmtList(String name, int enabledType);

//  EmployeeMgmtCheckSignUpResultResDto getCEODetailsById(Long id);
}