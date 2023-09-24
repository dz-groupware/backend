package com.example.backend.employee.mapper;

import com.example.backend.employee.dto.EmployeeCompanyDto;
import com.example.backend.employee.dto.EmployeeReqDto;
import com.example.backend.config.redis.PrincipalUserDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

  List<EmployeeReqDto> findEmployeeList(Long companyId, long offset, int limit);
  PrincipalUserDto findById(Long companyId);
  PrincipalUserDto findByLoginId(String loginId);
  void addEmployee(EmployeeReqDto employee);
  void modifyEmployee(EmployeeReqDto employee);
  void removeEmployee(Long id);
  long getTotalElements();
  Boolean checkMaster(Long empId);
  EmployeeCompanyDto findCompEmpByEmpId(Long employeeId);
}
