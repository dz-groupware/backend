package com.example.backend.department.service;

import com.example.backend.department.dto.DeptRes;
import com.example.backend.employee.dto.EmployeeReqDto;
import java.util.List;

public interface DepartmentService {
  void getDepartment();
  int addDepartment(DeptRes dept);
}
