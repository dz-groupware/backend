package com.example.backend.department.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.mapper.DepartmentMapper;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentMapper departmentMapper;

  public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
    this.departmentMapper = departmentMapper;
  }

  @Override
  public int addDepartment(DeptDto dept) {
    try{
      dept.setCompId(SecurityUtil.getCompanyId());
      departmentMapper.addDepartment(dept);
      return 1;
    } catch (Exception e) {
      return -1;
    }
  }

  @Override
  public List<DeptListDto> getDepartmentBasicList(Long compId) {
    return departmentMapper.getDepartmentBasicList(compId);
  }

  @Override
  public List<DeptListDto> getDepartmentById(Long compId, Long parId) {
    return departmentMapper.getDepartmentById(compId, parId);
  }

  @Override
  public DeptDto getBasicDetailById(Long id) {
    return departmentMapper.getBasicDetailById(id);
  }


}
