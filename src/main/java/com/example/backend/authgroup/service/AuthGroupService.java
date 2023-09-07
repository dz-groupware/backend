package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.Page;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import java.util.List;

public interface AuthGroupService {

  Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(int pageNumber,
      int pageSize);
  List<CompanyAuthSummaryDto> findCompanyAuthList(Long lastId, String orderBy,
      int pageSize);
//  long getCompanyAuthCount( Long departmentId, Long employeeId, String orderBy);
  long getCompanyAuthCount();
  List<CompanyMenuDto> getCompanyGnbList();
  List<CompanyMenuDto> getCompanyLnbList(Long parId);
  List<AuthMenuDto> getGnbListOfAuth(Long authId);
  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long authId);
  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);
}
