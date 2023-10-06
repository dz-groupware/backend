package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.authgroup.dto.AddEmpAuthDto;
import com.example.backend.authgroup.dto.EmployeeAuthStatusDto;
import com.example.backend.authgroup.dto.UpdateAuthDto;
import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.dto.Page;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import java.util.List;
import java.util.Map;

public interface AuthGroupService {

  List<CompanyAuthSummaryDto> findCompanyAuthListOrderById(Long lastId, Boolean canUseAuth, String orderBy, String searchTerm, int pageSize);
  List<CompanyAuthSummaryDto> findCompanyAuthListOrderByAuthName(String lastAuthName, Boolean canUseAuth,String orderBy, String searchTerm, int pageSize);
  long getCompanyAuthCount(Boolean canUseAuth);
  List<CompanyMenuDto> getCompanyGnbList(Boolean enabledYn);
  List<CompanyMenuDto> getCompanyLnbList(Long parId, Boolean enabledYn);
  List<AuthMenuDto> getGnbListOfAuth(Long authId);
  List<AuthMenuDto> getLnbListOfAuth(Long authId, Long parMenuId);
  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long authId);
  List<MenuAuthStatusDto> getLnbListOfAuthWithAll(Long authId, Long parId);
  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);
  Long addAuth(AddAuthDto addAuthDto);
  Long updateAuth(UpdateAuthDto updateAuthDto);

  void modifyMappedMenuOfAuth(Long authId, Map<Long, Boolean> checkedMenuItems);
  void deactivateAuthByAuthId(Long authId);


  List<CompanyAuthSummaryDto> findEmployeeAuthListOrderById(Long lastId, String orderBy, String searchTerm, Long employeeId, int pageSize);
  List<CompanyAuthSummaryDto> findEmployeeAuthListOrderByAuthName(String lastAuthName, String orderBy, String searchTerm, Long employeeId, int pageSize);
  Long getEmployeeAuthCount(Long employeeId);

  List<EmployeeAuthStatusDto> findEmployeeAuthStatusListOrderById(Long lastId, String orderBy, String searchTerm, Long employeeId, int pageSize);
  List<EmployeeAuthStatusDto> findEmployeeAuthStatusListOrderByAuthName(String lastAuthName, String orderBy, String searchTerm, Long employeeId, int pageSize);
  void addAuthEmployee(AddEmpAuthDto addEmpAuthDto);

  void deleteAuth(Long authId);
}