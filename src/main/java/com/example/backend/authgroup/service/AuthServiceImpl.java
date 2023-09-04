package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.UserListOfAuthDto;
import com.example.backend.common.Page;
import com.example.backend.common.PageDto;
import com.example.backend.authgroup.dto.AuthMenuDto;
import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
import com.example.backend.authgroup.dto.CompanyMenuDto;
import com.example.backend.authgroup.dto.MenuAuthStatusDto;
import com.example.backend.authgroup.mapper.AuthMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthMapper authMapper;

  public AuthServiceImpl(AuthMapper authMapper) {
    this.authMapper = authMapper;
  }

  @Override
  public Page<CompanyAuthSummaryDto> getCompanyAuthSummaryPage(Long companyId,
      int pageNumber,
      int pageSize) {
    long offset = (long) (pageNumber - 1) * pageSize;

    return new Page<>(authMapper.getCompanyAuthSummaryListForPage(companyId, offset, pageSize),
        new PageDto(pageNumber, pageSize,
            authMapper.getCompanyAuthCount(companyId, null, null, null))
    );
  }


  @Override
  public List<CompanyAuthSummaryDto> getCompanyAuthList(Long companyId,
      Long lastId,
      String orderBy,
      int pageSize) {

    return authMapper.getCompanyAuthList(companyId, lastId, orderBy, pageSize);
  }

  @Override
  public long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId,
      String orderBy) {
    return authMapper.getCompanyAuthCount(companyId, departmentId, employeeId, orderBy);
  }

  @Override
  public List<CompanyMenuDto> getCompanyGnbList(Long companyId) {
    return authMapper.getCompanyGnbList(companyId);
  }

  @Override
  public List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId) {
    return authMapper.getCompanyLnbList(companyId, parId);
  }

  @Override
  public List<AuthMenuDto> getGnbListOfAuth(Long companyId, Long authId) {
    return authMapper.getGnbListOfAuth(companyId,authId);
  }

  @Override
  public List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long companyId, Long authId) {
    return authMapper.getGnbListOfAuthWithAll(companyId, authId);
  }

  @Override
  public List<UserListOfAuthDto> getEmpListOfAuth(Long authId) {
    return authMapper.getEmpListOfAuth(authId);
  }


}
