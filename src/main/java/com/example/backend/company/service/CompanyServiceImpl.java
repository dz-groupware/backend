package com.example.backend.company.service;

import com.example.backend.common.Page;
import com.example.backend.common.PageDto;
import com.example.backend.company.dto.CompanyAuthSummaryDto;
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
        new PageDto(pageNumber,pageSize,companyMapper.countCompanyAuthSummaryList(companyId))
    );
  }


  @Override
  public long countCompanyList(Long companyId) {
    return companyMapper.countCompanyAuthSummaryList(companyId);
  }

  @Override
  public List<CompanyAuthSummaryDto> getCompanyAuthSummaryList(Long companyId,
                                                                Long afterAuthId,
                                                                int pageSize) {
    return companyMapper.getCompanyAuthSummaryList(companyId, afterAuthId, pageSize);
  }


}
