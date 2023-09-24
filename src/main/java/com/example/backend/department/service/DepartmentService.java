package com.example.backend.department.service;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.common.dto.PkDto;
import java.util.List;

public interface DepartmentService {
  int addDepartment(PkDto pkDto, DeptDto dept);
  List<DeptListDto> getDepartmentBasicList(PkDto pkDto);
  List<DeptListDto> getDepartmentById(PkDto pkDto, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  int modifyDepartment(DeptDto dept);
  int deleteDepartment(PkDto pkDto, Long id);
  int modifyAllDepartment(List<DeptDto> dept);
  List<DeptListDto> getOptionCompList(PkDto pkDto);
  List<DeptListDto> findDeptNameAndCode(Long compId, String text);

  int addDeptTest(DeptDto dept);
}
