package com.example.backend.employee.service;

import com.example.backend.employee.dto.EmployeeReqDto;
import com.example.backend.employee.dto.EmployeeResDto;
import java.util.List;

public interface EmployeeService {

  List<EmployeeReqDto> findEmployeeList(Long companyId, int pageNumber, int pageSize);

  EmployeeResDto findById(Long id);

  EmployeeResDto findByLoginId(String loginId);

  void addEmployee(EmployeeReqDto employee);

  void modifyEmployee(EmployeeReqDto employee);

  void removeEmployee(Long id);

  long getTotalElements();
}
