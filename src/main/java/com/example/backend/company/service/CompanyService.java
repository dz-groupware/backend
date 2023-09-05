package com.example.backend.company.service;

import com.example.backend.common.Page;
import com.example.backend.company.dto.CompanyAuthSummaryDto;
import java.util.List;

public interface CompanyService {

  Page<CompanyAuthSummaryDto> findCompanyAuthSummaryPage(Long companyId, int pageNumber,
      int pageSize);

  long countCompanyList(Long companyId);

  List<CompanyAuthSummaryDto> getCompanyAuthSummaryList(Long companyId, Long afterAuthId,
      int pageSize);
}
