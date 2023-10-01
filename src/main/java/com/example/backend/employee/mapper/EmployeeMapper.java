package com.example.backend.employee.mapper;

import com.example.backend.employee.dto.EmployeeCompanyDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

  EmployeeCompanyDto findCompEmpByEmpId(Long employeeId);
}
