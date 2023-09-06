package com.example.backend.employee.service;

import com.example.backend.employee.dto.EmployeeReqDto;
import com.example.backend.employee.mapper.EmployeeMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeMapper employeeMapper;

  public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
    this.employeeMapper = employeeMapper;
  }

  @Override
  public List<EmployeeReqDto> findEmployeeList(Long companyId, int pageNumber, int pageSize) {
    long offset = (long) (pageNumber - 1) * pageSize;
    return employeeMapper.findEmployeeList(companyId, offset, pageSize);
  }

  @Override
  public void addEmployee(EmployeeReqDto employee) {
    employeeMapper.addEmployee(employee);
  }

  @Override
  public void modifyEmployee(EmployeeReqDto employee) {
    employeeMapper.modifyEmployee(employee);
  }

  @Override
  public void removeEmployee(Long id) {
    employeeMapper.removeEmployee(id);
  }

  @Override
  public long getTotalElements() {
    return employeeMapper.getTotalElements();
  }
}
