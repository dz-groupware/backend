package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.Page;
import com.example.backend.common.PageDto;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import com.example.backend.authgroup.mapper.AuthGroupMapper;
import com.example.backend.config.jwt.SecurityUtil;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthGroupServiceImpl implements AuthGroupService {

  private final AuthGroupMapper authGroupMapper;

  public AuthGroupServiceImpl(AuthGroupMapper authGroupMapper) {
    this.authGroupMapper = authGroupMapper;
  }

  @Override
  public Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(Long companyId,
      int pageNumber,
      int pageSize) {
    long offset = (long) (pageNumber - 1) * pageSize;

    return new Page<>(authGroupMapper.getCompanyAuthSummaryListForPage(companyId, offset, pageSize),
        new PageDto(pageNumber, pageSize,
            authGroupMapper.getCompanyAuthCount(companyId, null, null, null))
    );
  }


  @Override
  public List<CompanyAuthSummaryDto> findCompanyAuthList(Long companyId,
      Long lastId,
      String orderBy,
      int pageSize) {

    return authGroupMapper.findCompanyAuthList(companyId, lastId, orderBy, pageSize);
  }

//  @Override
//  public long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId,
//      String orderBy) {
//    return authGroupMapper.getCompanyAuthCount(companyId, departmentId, employeeId, orderBy);
//  }

  @Override
  public long getCompanyAuthCount(Long departmentId, Long employeeId,
      String orderBy) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getCompanyAuthCount(companyId, departmentId, employeeId, orderBy);
  }


  @Override
  public List<CompanyMenuDto> getCompanyGnbList(Long companyId) {
    return authGroupMapper.getCompanyGnbList(companyId);
  }

  @Override
  public List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId) {
    return authGroupMapper.getCompanyLnbList(companyId, parId);
  }

  @Override
  public List<AuthMenuDto> getGnbListOfAuth(Long companyId, Long authId) {
    return authGroupMapper.getGnbListOfAuth(companyId,authId);
  }

  @Override
  public List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long companyId, Long authId) {
    return authGroupMapper.getGnbListOfAuthWithAll(companyId, authId);
  }

  @Override
  public List<UserListOfAuthDto> getEmpListOfAuth(Long authId) {
    return authGroupMapper.getEmpListOfAuth(authId);
  }


}