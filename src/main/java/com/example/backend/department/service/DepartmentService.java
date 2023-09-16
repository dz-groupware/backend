package com.example.backend.department.service;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import java.util.List;

public interface DepartmentService {
  int addDepartment(DeptDto dept);
  List<DeptListDto> getDepartmentBasicList(Long compId);
  List<DeptListDto> getDepartmentById(Long compId, Long parId);
  DeptDto getBasicDetailById(Long id);
}
