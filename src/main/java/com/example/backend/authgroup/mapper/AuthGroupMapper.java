package com.example.backend.authgroup.mapper;

import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import com.example.backend.authgroup.dto.UserListOfAuthDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthGroupMapper {

  List<CompanyAuthSummaryDto> getCompanyAuthSummaryListForPage(Long companyId, long offset,
      int limit);
  long getCompanyAuthCount(Long companyId);
  List<CompanyMenuDto> getCompanyGnbList(Long companyId, Boolean enabledYn);
  List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId, Boolean enabledYn);
  List<AuthMenuDto> getGnbListOfAuth(Long companyId, Long authId);
  List<AuthMenuDto> getLnbListOfAuth(Long companyId, Long authId, Long parMenuId);
  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long companyId, Long authId);
  List<MenuAuthStatusDto> getLnbListOfAuthWithAll(Long companyId, Long authId, Long parId);
  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);

  List<CompanyAuthSummaryDto> findCompanyAuthListOrderById(Long companyId, Long lastId, String orderBy, String searchTerm, int pageSize);
  List<CompanyAuthSummaryDto> findCompanyAuthListOrderByAuthName(Long companyId, String lastAuthName, String orderBy, String searchTerm, int pageSize);
  Long addAuth(AddAuthDto addAuthDto);
  void insertIntoAuthDashboard(Long compId, Long authId);
}