package com.example.backend.employeemgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface EmployeeMgmtService {

    List<EmployeeMgmtListResDto> getEmployeeMgmtList();

    List<EmployeeMgmtResDto> getEmployeeDetailsById(Long id);


    List<Map<Long, String>> getAllDepartmentMgmtList();
    List<EmployeeMgmtListResDto> findEmployeeMgmtList(Long compId, String text);
    @Transactional
    void addEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt);

    void removeEmployeeMgmt(Long id, EmployeeMgmtReqDto employeeMgmt);
}
