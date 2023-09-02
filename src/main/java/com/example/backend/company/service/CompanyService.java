package com.example.backend.company.service;

import com.example.backend.common.Page;
import com.example.backend.company.dto.AuthMenuDto;
import com.example.backend.company.dto.CompanyAuthSummaryDto;
import com.example.backend.company.dto.CompanyMenuDto;
import java.util.List;

public interface CompanyService {

  Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(Long companyId, int pageNumber,
      int pageSize);

  List<CompanyAuthSummaryDto> getCompanyAuthList(Long companyId, Long lastId, String orderBy,
      int pageSize);

  long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId, String orderBy);

  List<CompanyMenuDto> getCompanyGnbList(Long companyId);

  List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId);

  List<AuthMenuDto> getAuthMenuList(Long companyId, Long authId);
}
