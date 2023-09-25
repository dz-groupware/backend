package com.example.backend.company.mapper;

import com.example.backend.company.dto.CompanyWithEmployeeCountDto;
import com.example.backend.company.dto.DepartmentWithEmployeeCountDto;
import com.example.backend.company.dto.DeptEmployeeDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {
  CompanyWithEmployeeCountDto getParentCompanyWithEmployeeCount(Long companyId);
  List<CompanyWithEmployeeCountDto> getSubsidiaryCompaniesWithEmployeeCount(Long companyId);
  List<DepartmentWithEmployeeCountDto> getParDepartmentsWithEmployeeCount(Long companyId);
  List<DepartmentWithEmployeeCountDto> getSubsidiaryDepartmentsWithEmployeeCount(Long companyId, Long departmentId);
  List<DeptEmployeeDto> getEmployeeByDepartmentId(Long departmentId);
  List<DeptEmployeeDto> getEmployeeNoDepartment(Long companyId);
}
