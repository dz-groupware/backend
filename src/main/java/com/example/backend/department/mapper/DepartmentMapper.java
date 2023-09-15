package com.example.backend.department.mapper;

import com.example.backend.department.dto.DeptRes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
  void getDepartment();
  void addDepartment(DeptRes dept);

}
