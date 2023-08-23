package com.example.backend.employee.mapper;

import com.example.backend.employee.dto.EmployeeReqDto;
import com.example.backend.employee.dto.EmployeeResDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

  List<EmployeeReqDto> findEmployeeList(Long companyId, long offset, int limit);

  EmployeeResDto findById(Long companyId);

  EmployeeResDto findByLoginId(String loginId);

  void addEmployee(EmployeeReqDto employee);

  void modifyEmployee(EmployeeReqDto employee);

  void removeEmployee(Long id);

  long getTotalElements();
}
