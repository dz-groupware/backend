package com.example.backend.department.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.EmpListDto;
import java.util.List;

public interface DepartmentService {
  int addDepartment(DeptDto dept);
  List<DeptListDto> getDepartmentBasicList(Long compId);
  List<DeptListDto> getDepartmentById(Long compId, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  int modifyDepartment(DeptDto dept);
  int deleteDepartment(Long id);
  int modifyAllDepartment(List<DeptDto> dept);
  List<DeptListDto> getOptionCompList();
  List<DeptListDto> findDeptNameAndCode(Long compId, String text);
}
