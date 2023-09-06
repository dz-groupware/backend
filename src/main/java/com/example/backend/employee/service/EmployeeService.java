package com.example.backend.employee.service;

import com.example.backend.employee.dto.EmployeeReqDto;
import java.util.List;

public interface EmployeeService {

  List<EmployeeReqDto> findEmployeeList(Long companyId, int pageNumber, int pageSize);
  void addEmployee(EmployeeReqDto employee);
  void modifyEmployee(EmployeeReqDto employee);
  void removeEmployee(Long id);
  long getTotalElements();
}
