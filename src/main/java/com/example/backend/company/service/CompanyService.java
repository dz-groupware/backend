package com.example.backend.company.service;


import com.example.backend.company.dto.CompanyWithEmployeeCountDto;
import com.example.backend.company.dto.DepartmentWithEmployeeCountDto;
import com.example.backend.company.dto.DeptEmployeeDto;
import java.util.List;

public interface CompanyService {
  CompanyWithEmployeeCountDto getParentCompanyWithEmployeeCount();
  List<CompanyWithEmployeeCountDto> getSubsidiaryCompaniesWithEmployeeCount(Long companyId);

  List<DepartmentWithEmployeeCountDto> getParDepartmentsWithEmployeeCount(Long companyId);
  List<DepartmentWithEmployeeCountDto> getSubsidiaryDepartmentsWithEmployeeCount(Long companyId, Long departmentId);
  List<DeptEmployeeDto> getEmployeeByDepartmentId(Long departmentId);
  List<DeptEmployeeDto> getEmployeeNoDepartment(Long companyId);
}
