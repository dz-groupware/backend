package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.authgroup.dto.AddEmpAuthDto;
import com.example.backend.authgroup.dto.EmployeeAuthStatusDto;
import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.dto.Page;
import com.example.backend.common.dto.PageDto;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import com.example.backend.authgroup.mapper.AuthGroupMapper;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employee.mapper.EmployeeMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthGroupServiceImpl implements AuthGroupService{

  private final AuthGroupMapper authGroupMapper;
  private final EmployeeMapper employeeMapper;

  public AuthGroupServiceImpl(AuthGroupMapper authGroupMapper,
      EmployeeMapper employeeMapper) {
    this.authGroupMapper = authGroupMapper;
    this.employeeMapper = employeeMapper;
  }



  @Override
  public Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(int pageNumber, int pageSize) {
    long offset = (long) (pageNumber - 1) * pageSize;
    Long companyId = SecurityUtil.getCompanyId();
    return new Page<>(authGroupMapper.getCompanyAuthSummaryListForPage(companyId, offset, pageSize),
        new PageDto(pageNumber, pageSize,
            authGroupMapper.getCompanyAuthCount(companyId))
    );
  }



  @Override
  public List<CompanyAuthSummaryDto> findCompanyAuthListOrderById(Long lastId, String orderBy,
      String searchTerm, int pageSize) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.findCompanyAuthListOrderById(companyId, lastId, orderBy, searchTerm, pageSize);

  }

  @Override
  public List<CompanyAuthSummaryDto> findCompanyAuthListOrderByAuthName(String lastAuthName,
      String orderBy, String searchTerm, int pageSize) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.findCompanyAuthListOrderByAuthName(companyId, lastAuthName, orderBy, searchTerm, pageSize);
  }

  @Override
  public long getCompanyAuthCount() {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getCompanyAuthCount(companyId);
  }

  @Override
  public List<CompanyMenuDto> getCompanyGnbList(Boolean enabledYn) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getCompanyGnbList(companyId,enabledYn);
  }

  @Override
  public List<CompanyMenuDto> getCompanyLnbList(Long parId, Boolean enabledYn) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getCompanyLnbList(companyId, parId, enabledYn);
  }

  @Override
  public List<AuthMenuDto> getGnbListOfAuth(Long authId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getGnbListOfAuth(companyId,authId);
  }

  @Override
  public List<AuthMenuDto> getLnbListOfAuth(Long authId, Long parMenuId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getLnbListOfAuth(companyId,authId, parMenuId);
  }

  @Override
  public List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long authId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getGnbListOfAuthWithAll(companyId, authId);
  }

  @Override
  public List<MenuAuthStatusDto> getLnbListOfAuthWithAll(Long authId, Long parId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getLnbListOfAuthWithAll(companyId, authId, parId);
  }

  @Override
  public List<UserListOfAuthDto> getEmpListOfAuth(Long authId) {
    return authGroupMapper.getEmpListOfAuth(authId);
  }

  @Transactional
  @Override
  public Long addAuth(AddAuthDto addAuthDto) {
    Long companyId = SecurityUtil.getCompanyId();
    authGroupMapper.addAuth(addAuthDto);
    authGroupMapper.insertIntoAuthDashboard(companyId, addAuthDto.getId());
    return addAuthDto.getId();
  }

  @Transactional
  @Override
  public void modifyMappedMenuOfAuth(Long authId, Map<Long, Boolean> checkedMenuItems) {
    authGroupMapper.deleteAuthMenuByAuthId(authId);
    //체크가 true인것만 가져오기
    List<Long> checkedMenuIds = checkedMenuItems.entrySet().stream()
        .filter(entry -> entry.getValue())
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    // true로 마킹된 메뉴 아이템이 있을 경우에만 인서트를 수행
    if(!checkedMenuIds.isEmpty()) {
      authGroupMapper.modifyMappedMenuOfAuth(authId, checkedMenuIds);
    }
  }

  @Transactional
  @Override
  public void deactivateAuthByAuthId(Long authId) {
    authGroupMapper.deactivateAuthByAuthId(authId);
  }

  @Override
  public List<CompanyAuthSummaryDto> findEmployeeAuthListOrderById(Long lastId, String orderBy,
      String searchTerm, Long employeeId, int pageSize) {
    return authGroupMapper.findEmployeeAuthListOrderById(lastId,orderBy,searchTerm, employeeId, pageSize);
  }

  @Override
  public List<CompanyAuthSummaryDto> findEmployeeAuthListOrderByAuthName(String lastAuthName,
      String orderBy, String searchTerm, Long employeeId, int pageSize) {
    return authGroupMapper.findEmployeeAuthListOrderByAuthName(lastAuthName, orderBy, searchTerm, employeeId, pageSize);
  }

  @Override
  public Long getEmployeeAuthCount(Long employeeId) {
    return authGroupMapper.getEmployeeAuthCount( employeeId);
  }

  @Override
  public List<EmployeeAuthStatusDto> findEmployeeAuthStatusListOrderById(Long lastId, String orderBy,
      String searchTerm, Long employeeId, int pageSize) {
    Long companyId = employeeMapper.findCompEmpByEmpId(employeeId).getCompanyId();
    return authGroupMapper.findEmployeeAuthStatusListOrderById(lastId, orderBy, searchTerm, companyId, employeeId, pageSize);
  }

  @Override
  public List<EmployeeAuthStatusDto> findEmployeeAuthStatusListOrderByAuthName(String lastAuthName,
      String orderBy, String searchTerm, Long employeeId, int pageSize) {
    Long companyId = employeeMapper.findCompEmpByEmpId(employeeId).getCompanyId();
    return authGroupMapper.findEmployeeAuthStatusListOrderByAuthName(lastAuthName, orderBy, searchTerm, companyId, employeeId, pageSize);
  }


  @Transactional
  @Override
  public void addAuthEmployee(AddEmpAuthDto addEmpAuthDto) {
    Long empId = addEmpAuthDto.getEmployeeId();
    authGroupMapper.deleteAuthEmployeeByEmpId(empId);
    List<Long> checkedAuthIds = addEmpAuthDto.getSelectedAuthIds().entrySet().stream()
        .filter(entry -> entry.getValue())
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    if(!checkedAuthIds.isEmpty()) { // 리스트가 비어있지 않을 경우에만 추가
      authGroupMapper.addAuthEmployee(empId, checkedAuthIds);
    }
  }
}
