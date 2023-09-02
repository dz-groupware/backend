package com.example.backend.company.service;

import com.example.backend.common.Page;
import com.example.backend.common.PageDto;
import com.example.backend.company.dto.AuthMenuDto;
import com.example.backend.company.dto.CompanyAuthSummaryDto;
import com.example.backend.company.dto.CompanyMenuDto;
import com.example.backend.company.mapper.CompanyMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyMapper companyMapper;

  public CompanyServiceImpl(CompanyMapper companyMapper) {
    this.companyMapper = companyMapper;
  }

  @Override
  public Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(Long companyId,
      int pageNumber,
      int pageSize) {
    long offset = (long) (pageNumber - 1) * pageSize;

    return new Page<>(companyMapper.getCompanyAuthSummaryListForPage(companyId, offset, pageSize),
        new PageDto(pageNumber, pageSize,
            companyMapper.getCompanyAuthCount(companyId, null, null, null))
    );
  }


  @Override
  public List<CompanyAuthSummaryDto> getCompanyAuthList(Long companyId,
      Long lastId,
      String orderBy,
      int pageSize) {

    return companyMapper.getCompanyAuthList(companyId, lastId, orderBy, pageSize);
  }

  @Override
  public long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId,
      String orderBy) {
    return companyMapper.getCompanyAuthCount(companyId, departmentId, employeeId, orderBy);
  }

  @Override
  public List<CompanyMenuDto> getCompanyGnbList(Long companyId) {
    return companyMapper.getCompanyGnbList(companyId);
  }

  @Override
  public List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId) {
    return companyMapper.getCompanyLnbList(companyId, parId);
  }

  @Override
  public List<AuthMenuDto> getAuthMenuList(Long companyId, Long authId) {
    return companyMapper.getAuthMenuList(companyId, authId);
  }


}
