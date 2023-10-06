package com.example.backend.department.mapper;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.DeptTrans;
import com.example.backend.department.dto.EmpListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {

  List<DeptListDto> getDepartmentBasicList(Long compId);
  List<DeptListDto> getDepartmentById(Long compId, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  void addDepartment(DeptDto dept);
  void modifyDepartment(DeptDto dept);
  void deleteDepartment(Long compId, Long id, String StringId);
  List<DeptListDto> getOptionCompList(Long id);
  List<DeptListDto> findDeptNameAndCode(Long compId, String text1, String text2);
  int checkDeptCode(String text, Long id);


  int checkDeptInDept(String id1, String id2, Long parId);
  DeptTrans getOriginDept (Long id);
  List<DeptTrans> getMoveDeptList (String id);
  void modifyDeptTree(DeptTrans dept);
  DeptTrans getParDept(Long id);
  void modifyUpperDeptCNY(Long parId);
}
