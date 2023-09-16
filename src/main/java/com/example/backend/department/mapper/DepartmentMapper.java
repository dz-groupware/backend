package com.example.backend.department.mapper;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
  void addDepartment(DeptDto dept);
  List<DeptListDto> getDepartmentBasicList(Long compId);
  List<DeptListDto> getDepartmentById(Long compId, Long parId);
  DeptDto getBasicDetailById(Long id);

}
