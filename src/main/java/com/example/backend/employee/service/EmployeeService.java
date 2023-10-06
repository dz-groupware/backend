package com.example.backend.employee.service;

import com.example.backend.employee.dto.EmployeeCompanyDto;
import com.example.backend.employee.dto.EmployeeMDto;
import com.example.backend.employee.dto.UpdateMasterYnRequest;

public interface EmployeeService {
  EmployeeCompanyDto findCompEmpByEmpId(Long employeeId);
  boolean isMaster(Long employeeId);
  Long findCompIdOfEmpId(Long employeeId);
  EmployeeMDto changeMasterYn(UpdateMasterYnRequest request);
}
