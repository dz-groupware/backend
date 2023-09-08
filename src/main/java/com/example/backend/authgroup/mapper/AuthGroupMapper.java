package com.example.backend.authgroup.mapper;

import com.example.backend.authgroup.dto.AuthMenuDto;
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
  List<CompanyAuthSummaryDto> findCompanyAuthList(Long companyId, Long lastId, String orderBy, String searchTerm,
      int limit);
  long getCompanyAuthCount(Long companyId);
  List<CompanyMenuDto> getCompanyGnbList(Long companyId);
  List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId);
  List<AuthMenuDto> getGnbListOfAuth(Long companyId, Long authId);
  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long companyId, Long authId);
  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);
}