package com.example.backend.company.service;

import com.example.backend.company.dto.CompanyWithEmployeeCountDto;
import com.example.backend.company.dto.DepartmentWithEmployeeCountDto;
import com.example.backend.company.dto.DeptEmployeeDto;
import com.example.backend.company.mapper.CompanyMapper;
import com.example.backend.config.jwt.SecurityUtil;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService{

  private final CompanyMapper companyMapper;

  public CompanyServiceImpl(CompanyMapper companyMapper) {
    this.companyMapper = companyMapper;
  }

  @Override
  public CompanyWithEmployeeCountDto getParentCompanyWithEmployeeCount() {
    Long companyId = SecurityUtil.getCompanyId();
    return companyMapper.getParentCompanyWithEmployeeCount(companyId);
  }

  @Override
  public List<CompanyWithEmployeeCountDto> getSubsidiaryCompaniesWithEmployeeCount(Long companyId) {
    return companyMapper.getSubsidiaryCompaniesWithEmployeeCount(companyId);
  }

  @Override
  public List<DepartmentWithEmployeeCountDto> getParDepartmentsWithEmployeeCount(Long companyId) {
    return companyMapper.getParDepartmentsWithEmployeeCount(companyId);
  }

  @Override
  public List<DepartmentWithEmployeeCountDto> getSubsidiaryDepartmentsWithEmployeeCount(Long companyId,
      Long departmentId) {
    return companyMapper.getSubsidiaryDepartmentsWithEmployeeCount(companyId, departmentId);
  }

  @Override
  public List<DeptEmployeeDto> getEmployeeByDepartmentId(Long departmentId) {
    return companyMapper.getEmployeeByDepartmentId(departmentId);
  }

  @Override
  public List<DeptEmployeeDto> getEmployeeNoDepartment(Long companyId) {
    return companyMapper.getEmployeeNoDepartment(companyId);
  }
}
