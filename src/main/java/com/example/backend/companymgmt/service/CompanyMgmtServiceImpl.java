package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import com.example.backend.companymgmt.mapper.CompanyMgmtMapper;
import java.util.List;

import com.example.backend.config.redis.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class CompanyMgmtServiceImpl implements CompanyMgmtService {

  private final CompanyMgmtMapper companyMgmtMapper;

  public CompanyMgmtServiceImpl(CompanyMgmtMapper companyMgmtMapper) {
    this.companyMgmtMapper = companyMgmtMapper;
  }

  @Override
  public List<CompanyMgmtListResDto> getCompanyMgmtList() {
    Long companyId = SecurityUtil.getCompanyId();
    return companyMgmtMapper.getCompanyMgmtList(companyId);
  }

  @Override
  public CompanyMgmtResDto getCompanyDetailsById(Long id) {
    Long companyId = SecurityUtil.getCompanyId();
    return companyMgmtMapper.getCompanyDetailsById(id,companyId);
  }

  @Override
  public List<CompanyMgmtListResDto> findCompanyMgmtList(String name, int enabledType) {
    Long companyId = SecurityUtil.getCompanyId();
    if (enabledType == 2) {
      return companyMgmtMapper.findAllCompanyMgmtList(companyId,name);
    }

    Boolean enabled = enabledType==1?true:false;

    return companyMgmtMapper.findCompanyMgmtList(companyId,name, enabled);
  }

  @Override
  public void addCompanyMgmt(CompanyMgmtReqDto companyMgmt) {
    Long companyId = SecurityUtil.getCompanyId();
    companyMgmtMapper.addCompanyMgmt(companyId,companyMgmt);
  }

  @Override
  public void modifyCompanyMgmt(Long id, CompanyMgmtReqDto companyMgmt) {
    Long companyId = SecurityUtil.getCompanyId();
    companyMgmt.setId(id);
    companyMgmt.setCompanyId(id);
    companyMgmtMapper.modifyCompanyMgmt(companyMgmt);
  }

  @Override
  public void removeCompanyMgmt(Long id) {
    Long companyId = SecurityUtil.getCompanyId();
    companyMgmtMapper.removeCompanyMgmt(companyId,id);
  }
}
