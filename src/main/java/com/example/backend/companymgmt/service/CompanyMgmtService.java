package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;

import java.util.List;

public interface CompanyMgmtService{

    List<CompanyMgmtResDto> findCompanyMgmtList(boolean deletedYn);
    CompanyMgmtResDto findCompanyDetailsByCode(String code);

    List<CompanyMgmtResDto> searchCompanyMgmt(String name, int enabledYn);
    void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);
    void modifyCompanyMgmt(String code,CompanyMgmtReqDto companyMgmt);
    void removeCompanyMgmt(String code, CompanyMgmtReqDto companyMgmt);




}