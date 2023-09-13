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
  public List<CompanyMenuDto> getCompanyGnbList() {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getCompanyGnbList(companyId);
  }

  @Override
  public List<CompanyMenuDto> getCompanyLnbList(Long parId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getCompanyLnbList(companyId, parId);
  }

  @Override
  public List<AuthMenuDto> getGnbListOfAuth(Long authId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getGnbListOfAuth(companyId,authId);
  }

  @Override
  public List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long authId) {
    Long companyId = SecurityUtil.getCompanyId();
    return authGroupMapper.getGnbListOfAuthWithAll(companyId, authId);
  }

  @Override
  public List<UserListOfAuthDto> getEmpListOfAuth(Long authId) {
    return authGroupMapper.getEmpListOfAuth(authId);
  }
}
