package com.example.backend.employeemgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;

import java.util.List;

public interface EmployeeMgmtService {

    List<EmployeeMgmtListResDto> getEmployeeMgmtList();
}
