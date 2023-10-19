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
  List<DeptListDto> getDepartmentById(Long compId, Long id, Long parId);
  DeptDto getBasicDetailById(Long id);
  List<EmpListDto> getEmpListByDeptId(Long id);
  void addDepartment(DeptDto dept);
  void modifyDepartment(DeptDto dept);
  void modifyDepartmentAndParId(DeptDto dept);
  void deleteDepartment(Long compId, Long id, String StringId);
  List<DeptListDto> findDeptNameAndCode(Long compId, String text1, String text2);
  int getCountSearchDept(Long compId, String text1, String text2);
  int checkDeptCode(String text, Long id);
  Long isHeadCompany(Long compId);
  List<DeptListDto> getOptionCompList (String text, Long id);

  int checkDeptInDept(Long parId, String id1, String id2, String id3);
  DeptTrans getOriginDept (Long id);
  List<DeptTrans> getMoveDeptList (String id1, String id2, String id3);
  void modifyDeptTree(DeptTrans dept);

  int getIsChildNodeYn(String id1, String id2);

  DeptTrans getParDept(Long id);
  void modifyUpperDeptCNY(Long parId);
  void modifyUpperDeptCNN(Long parId);
}
