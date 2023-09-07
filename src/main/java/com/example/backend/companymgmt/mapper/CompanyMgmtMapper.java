package com.example.backend.companymgmt.mapper;


import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMgmtMapper {

  List<CompanyMgmtListResDto> getCompanyMgmtList(Long companyId);

  CompanyMgmtResDto getCompanyDetailsById(Long id,Long companyId);

  List<CompanyMgmtListResDto> findAllCompanyMgmtList(Long companyId,String name);

  List<CompanyMgmtListResDto> findCompanyMgmtList(Long companyId,String name, Boolean enabledYn);

  void addCompanyMgmt(Long companyId,CompanyMgmtReqDto companyMgmt);

  void modifyCompanyMgmt(CompanyMgmtReqDto companyMgmt);


  void removeCompanyMgmt(Long companyId,Long id);


}
