package com.example.backend.employee.service;

import com.example.backend.employee.dto.EmployeeCompanyDto;
import com.example.backend.employee.dto.UpdateMasterYnRequest;
import com.example.backend.employee.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{
  private final EmployeeMapper employeeMapper;

  public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
    this.employeeMapper = employeeMapper;
  }

  @Override
  public EmployeeCompanyDto findCompEmpByEmpId(Long employeeId) {
    return employeeMapper.findCompEmpByEmpId(employeeId);
  }

  @Override
  public boolean isMaster(Long employeeId) {
    return employeeMapper.isMaster(employeeId);
  }

  @Override
  public Long findCompIdOfEmpId(Long employeeId) {
    return employeeMapper.findCompIdOfEmpId(employeeId);
  }

  @Override
  public boolean changeMasterYn(UpdateMasterYnRequest request) {
    int updatedRows = employeeMapper.changeMasterYn(request);
    if (updatedRows > 0) {
      return true;
    }
    return false;
  }
}
