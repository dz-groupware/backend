package com.example.backend.department.service;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.config.jwt.PkDto;
import java.util.List;

public interface DepartmentService {
  List<DeptListDto> getDepartmentBasicList(PkDto pkDto);
  List<DeptListDto> getDepartmentById(PkDto pkDto, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  int addDepartment(PkDto pkDto, DeptDto dept);
  int modifyDepartment(DeptDto dept);
  int deleteDepartment(PkDto pkDto, Long id);
  List<DeptListDto> getOptionCompList(PkDto pkDto);
  List<DeptListDto> findDeptNameAndCode(Long compId, String text);
  boolean checkDeptCode(String text, Long id);
  int modifyAllDepartment(List<DeptDto> dept);
}
