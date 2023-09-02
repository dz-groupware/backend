package com.example.backend.company.mapper;

import com.example.backend.company.dto.AuthMenuDto;
import com.example.backend.company.dto.CompanyAuthSummaryDto;
import com.example.backend.company.dto.CompanyMenuDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {

  List<CompanyAuthSummaryDto> getCompanyAuthSummaryListForPage(Long companyId, long offset,
      int limit);

  List<CompanyAuthSummaryDto> getCompanyAuthList(Long companyId, Long lastId, String orderBy,
      int limit);

  long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId, String orderBy);

  List<CompanyMenuDto> getCompanyGnbList(Long companyId);

  List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId);

  List<AuthMenuDto> getAuthMenuList(Long companyId, Long authId);
}
