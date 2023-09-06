//package com.example.backend.authgroup.mapper;
//
//<<<<<<< HEAD
//import com.example.backend.authgroup.dto.response.AuthResDto;
//=======
//import com.example.backend.authgroup.dto.AuthMenuDto;
//import com.example.backend.authgroup.dto.CompanyAuthSummaryDto;
//import com.example.backend.authgroup.dto.CompanyMenuDto;
//import com.example.backend.authgroup.dto.MenuAuthStatusDto;
//import com.example.backend.authgroup.dto.UserListOfAuthDto;
//
//import java.util.List;
//import org.apache.ibatis.annotations.Mapper;
//
//@Mapper
//public interface AuthMapper {
//
//
//  List<AuthResDto> findAuthList(Long companyId, long offset, int limit);
//
//  long getTotalElements();
//
//  List<CompanyAuthSummaryDto> getCompanyAuthSummaryListForPage(Long companyId, long offset,
//      int limit);
//
//  List<CompanyAuthSummaryDto> getCompanyAuthList(Long companyId, Long lastId, String orderBy,
//      int limit);
//
//  long getCompanyAuthCount(Long companyId, Long departmentId, Long employeeId, String orderBy);
//
//  List<CompanyMenuDto> getCompanyGnbList(Long companyId);
//
//  List<CompanyMenuDto> getCompanyLnbList(Long companyId, Long parId);
//
//  List<AuthMenuDto> getGnbListOfAuth(Long companyId, Long authId);
//
//  List<MenuAuthStatusDto> getGnbListOfAuthWithAll(Long companyId, Long authId);
//
//  List<UserListOfAuthDto> getEmpListOfAuth(Long authId);
//
//}
