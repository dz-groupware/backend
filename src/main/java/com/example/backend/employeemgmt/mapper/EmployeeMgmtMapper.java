package com.example.backend.employeemgmt.mapper;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMgmtMapper {
    List<EmployeeMgmtListResDto> getEmployeeMgmtList(Long companyId);

}
