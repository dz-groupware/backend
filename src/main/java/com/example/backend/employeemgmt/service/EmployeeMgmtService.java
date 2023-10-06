package com.example.backend.employeemgmt.service;

import com.example.backend.employeemgmt.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface EmployeeMgmtService {

    List<EmployeeMgmtListResDto> getEmployeeMgmtList();

    List<EmployeeMgmtListResDto> getIncumbentEmployeeMgmtList();

    List<EmployeeMgmtListResDto> getQuitterEmployeeMgmtList();

    List<EmployeeMgmtResDto> getEmployeeDetailsById(Long id);


    List<Map<Long, String>> getAllDepartmentMgmtList();
    List<EmployeeMgmtListResDto> findEmployeeMgmtList(Long compId, String text);
    @Transactional
    void addEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt);

    void modifyEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt);
    void removeEmployeeMgmt(Long id, EmployeeMgmtReqDto employeeMgmt);

    Boolean checkLoginId(String loginId);


    @Transactional
    EmployeeMgmtCheckSignUpResultResDto checkSignUp(EmployeeMgmtSignUpReqDto employeeMgmt);
}
