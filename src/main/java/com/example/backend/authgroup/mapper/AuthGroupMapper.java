package com.example.backend.authgroup.mapper;

import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.EmployeeAuthStatusDto;
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
  void addAuth(AddAuthDto addAuthDto);
  void insertIntoAuthDashboard(Long compId, Long authId);
  void deleteAuthMenuByAuthId(Long authId);
  void modifyMappedMenuOfAuth(Long authId, List<Long> checkedMenuIds);
  void deactivateAuthByAuthId(Long authId);

  List<CompanyAuthSummaryDto> findEmployeeAuthListOrderById(Long lastId, String orderBy, String searchTerm,Long employeeId, int pageSize);
  List<CompanyAuthSummaryDto> findEmployeeAuthListOrderByAuthName(String lastAuthName, String orderBy, String searchTerm, Long employeeId, int pageSize);
  long getEmployeeAuthCount( Long employeeId);

  List<EmployeeAuthStatusDto> findEmployeeAuthStatusListOrderById(Long lastId, String orderBy, String searchTerm, Long companyId, Long employeeId, int pageSize);
  List<EmployeeAuthStatusDto> findEmployeeAuthStatusListOrderByAuthName(String lastAuthName, String orderBy, String searchTerm, Long companyId, Long employeeId, int pageSize);
  void deleteAuthEmployeeByEmpId(Long employeeId);
  void addAuthEmployee(Long employeeId, List<Long> checkedAuthIds);

  List<CompanyAuthSummaryDto> findMasterAuthListOrderById(Long lastId, String orderBy, String searchTerm, Long companyId, int pageSize);
  List<CompanyAuthSummaryDto> findMasterAuthListOrderByAuthName(String lastAuthName, String orderBy, String searchTerm, Long companyId, int pageSize);
}