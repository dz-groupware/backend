package com.example.backend.companymgmt.mapper;


import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMgmtMapper {

  List<CompanyMgmtResDto> findCompanyMgmtList();

  CompanyMgmtResDto findCompanyDetailsById(int id);

  List<CompanyMgmtResDto> searchAllCompanyMgmt(String name);

  List<CompanyMgmtResDto> searchCompanyMgmt(String name, int enabledYn);

  void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);

  void modifyCompanyMgmt(CompanyMgmtReqDto companyMgmt);

  int removeCompanyMgmt(CompanyMgmtReqDto companyMgmt);


}
