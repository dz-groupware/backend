package com.example.backend.employee.service;

import com.example.backend.employee.dto.request.EmpReqDto;
import com.example.backend.employee.dto.response.EmpResDto;

import java.util.List;

public interface EmployeeService {
    List<EmpReqDto> findEmpList(Long companyId, int pageNumber, int pageSize);
    EmpResDto findById(Long id);
    EmpResDto findByLoginId(String loginId);
    void addEmployee(EmpReqDto employee);
    void modifyEmployee(EmpReqDto employee);
    void removeEmployee(Long id);
    long getTotalElements();
}
