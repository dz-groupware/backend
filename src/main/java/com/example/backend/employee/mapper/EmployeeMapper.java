package com.example.backend.employee.mapper;

import com.example.backend.employee.dto.EmployeeCompanyDto;
import com.example.backend.employee.dto.UpdateMasterYnRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

  EmployeeCompanyDto findCompEmpByEmpId(Long employeeId);
  boolean isMaster(Long employeeId);
  Long findCompIdOfEmpId(Long employeeId);
  int changeMasterYn(UpdateMasterYnRequest request);
}
