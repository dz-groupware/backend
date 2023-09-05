package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.Page;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import java.util.List;

public interface AuthService {

  Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(Long companyId, int pageNumber,
      int pageSize);

  List<CompanyAuthSummaryDto> getCompanyAuthList(Long companyId, Long lastId, String orderBy,
      int pageSize);

  long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId, String orderBy);

  List<CompanyMenuDto> getCompanyGnbList(Long companyId);

  List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId);

  List<AuthMenuDto> getGnbListOfAuth(Long companyId, Long authId);

  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long companyId, Long authId);

  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);
}
