package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import java.util.List;

public interface CompanyMgmtService {

  List<CompanyMgmtListResDto> getCompanyMgmtList();

  CompanyMgmtResDto getCompanyDetailsById(Long id);

  List<CompanyMgmtListResDto> findCompanyMgmtList(String name, int enabledType);

  void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);

  void modifyCompanyMgmt(Long id, CompanyMgmtReqDto companyMgmt);

  void removeCompanyMgmt(Long id);


}