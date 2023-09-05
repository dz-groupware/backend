package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import java.util.List;

public interface CompanyMgmtService {

  List<CompanyMgmtResDto> findCompanyMgmtList();

  CompanyMgmtResDto findCompanyDetailsById(int id);

  List<CompanyMgmtResDto> searchCompanyMgmt(String name, int enabledYn);

  void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);

  void modifyCompanyMgmt(int id, CompanyMgmtReqDto companyMgmt);

  void removeCompanyMgmt(int id, CompanyMgmtReqDto companyMgmt);


}