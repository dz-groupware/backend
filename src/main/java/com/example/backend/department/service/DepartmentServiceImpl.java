package com.example.backend.department.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.department.dto.DeptRes;
import com.example.backend.department.mapper.DepartmentMapper;
import com.example.backend.employee.dto.EmployeeReqDto;
import com.example.backend.employee.mapper.EmployeeMapper;
import com.example.backend.employee.service.EmployeeService;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentMapper departmentMapper;

  public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
    this.departmentMapper = departmentMapper;
  }

  @Override
  public void getDepartment() {
    departmentMapper.getDepartment();
  }

  @Override
  public int addDepartment(DeptRes dept) {
    try{
      dept.setCompId(SecurityUtil.getCompanyId());
      departmentMapper.addDepartment(dept);
      return 1;
    } catch (Exception e) {
      return -1;
    }
  }
}
