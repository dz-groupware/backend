package com.example.backend.employee.service;

import com.example.backend.common.error.BusinessLogicException;
import com.example.backend.common.error.code.UserExceptionCode;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employee.dto.EmployeeCompanyDto;
import com.example.backend.employee.dto.EmployeeMDto;
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
  public EmployeeMDto changeMasterYn(UpdateMasterYnRequest request) {
    if(SecurityUtil.getEmployeeId().equals(request.getEmpId())) {
      throw new BusinessLogicException(UserExceptionCode.UnchangeableSelfAuthority);
    }
    int updatedRows = employeeMapper.changeMasterYn(request);
    if (updatedRows > 0) {
      return employeeMapper.getEmployeeAfterUpdateMaster(request.getEmpId());
    }
    return null;
  }
}
