package com.example.backend.companymgmt.mapper;


import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMgmtMapper {

  List<CompanyMgmtListResDto> getCompanyMgmtList();

  CompanyMgmtResDto getCompanyDetailsById(Long id);

  List<CompanyMgmtListResDto> findAllCompanyMgmtList(String name);

  List<CompanyMgmtListResDto> findCompanyMgmtList(String name, Boolean enabledYn);

  void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);

  void modifyCompanyMgmt(CompanyMgmtReqDto companyMgmt);

  int removeCompanyMgmt(Long id);


}
