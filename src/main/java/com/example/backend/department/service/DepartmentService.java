package com.example.backend.department.service;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.setting.dto.JwtDto;
import java.util.List;

public interface DepartmentService {
  int addDepartment(JwtDto jwtDto, DeptDto dept);
  List<DeptListDto> getDepartmentBasicList(JwtDto jwtDto);
  List<DeptListDto> getDepartmentById(JwtDto jwtDto, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  int modifyDepartment(DeptDto dept);
  int deleteDepartment(JwtDto jwtDto, Long id);
  int modifyAllDepartment(List<DeptDto> dept);
  List<DeptListDto> getOptionCompList(JwtDto jwtDto);
  List<DeptListDto> findDeptNameAndCode(Long compId, String text);

  int addDeptTest(DeptDto dept);
}
