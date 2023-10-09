package com.example.backend.department.service;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.config.jwt.PkDto;
import java.util.List;

public interface DepartmentService {
  List<DeptListDto> getDepartmentBasicList();
  List<DeptListDto> getDepartmentById(Long compId, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  int addDepartment(DeptDto dept);
  int modifyDepartment(DeptDto dept);
  int deleteDepartment(Long id);
  List<DeptListDto> getOptionCompList();
  List<DeptListDto> findDeptNameAndCode(Long compId, String text);
  boolean checkDeptCode(String text, Long id);
  int modifyAllDepartment(List<DeptDto> dept);
}
