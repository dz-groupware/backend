package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import com.example.backend.companymgmt.mapper.CompanyMgmtMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompanyMgmtServiceImpl implements CompanyMgmtService {

  private final CompanyMgmtMapper companyMgmtMapper;

  public CompanyMgmtServiceImpl(CompanyMgmtMapper companyMgmtMapper) {
    this.companyMgmtMapper = companyMgmtMapper;
  }

  @Override
  public List<CompanyMgmtListResDto> getCompanyMgmtList() {
    return companyMgmtMapper.getCompanyMgmtList();
  }

  @Override
  public CompanyMgmtResDto getCompanyDetailsById(Long id) {
    return companyMgmtMapper.getCompanyDetailsById(id);
  }

  @Override
  public List<CompanyMgmtListResDto> findCompanyMgmtList(String name, int enabledType) {
    if (enabledType == 2) {
      return companyMgmtMapper.findAllCompanyMgmtList(name);
    }

    Boolean enabled = enabledType==1?true:false;

    return companyMgmtMapper.findCompanyMgmtList(name, enabled);
  }

  @Override
  public void addCompanyMgmt(CompanyMgmtReqDto companyMgmt) {
    companyMgmtMapper.addCompanyMgmt(companyMgmt);
  }

  @Override
  public void modifyCompanyMgmt(Long id, CompanyMgmtReqDto companyMgmt) {
    companyMgmt.setId(id);
    companyMgmtMapper.modifyCompanyMgmt(companyMgmt);
  }

  @Override
  public void removeCompanyMgmt(Long id) {
    companyMgmtMapper.removeCompanyMgmt(id);
  }
}
