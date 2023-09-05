package com.example.backend.company.mapper;

import com.example.backend.company.dto.CompanyAuthSummaryDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {

  List<CompanyAuthSummaryDto> getCompanyAuthSummaryListForPage(Long companyId, long offset,
      int limit);

  long countCompanyAuthSummaryList(Long companyId);

  List<CompanyAuthSummaryDto> getCompanyAuthSummaryList(Long companyId, Long afterAuthId,
      int limit);
}
