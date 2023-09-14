package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.Page;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import java.util.List;

public interface AuthGroupService {

  Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(int pageNumber, int pageSize);
  List<CompanyAuthSummaryDto> findCompanyAuthListOrderById(Long lastId, String orderBy, String searchTerm, int pageSize);
  List<CompanyAuthSummaryDto> findCompanyAuthListOrderByAuthName(String lastAuthName, String orderBy, String searchTerm, int pageSize);
  long getCompanyAuthCount();
  List<CompanyMenuDto> getCompanyGnbList(Boolean enabledYn);
  List<CompanyMenuDto> getCompanyLnbList(Long parId, Boolean enabledYn);
  List<AuthMenuDto> getGnbListOfAuth(Long authId);
  List<AuthMenuDto> getLnbListOfAuth(Long authId, Long parMenuId);
  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long authId);
  List<MenuAuthStatusDto> getLnbListOfAuthWithAll(Long authId, Long parId);
  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);
  Long addAuth(AddAuthDto addAuthDto);
}