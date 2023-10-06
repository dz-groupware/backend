package com.example.backend.company.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.company.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/par")
  public ResponseEntity<?> getParentCompanyWithEmployeeCount() {
    return ResponseEntity.ok(new SingleResponseDto<>(companyService.getParentCompanyWithEmployeeCount()));
  }

  @GetMapping("/par/{company-id}/sub")
  public ResponseEntity<?> getSubsidiaryCompaniesWithEmployeeCount(@PathVariable("company-id") Long companyId) {
    return ResponseEntity.ok(new SingleResponseDto<>(companyService.getSubsidiaryCompaniesWithEmployeeCount(companyId)));
  }

  @GetMapping("/{company-id}/departments/par")
  public ResponseEntity<?> getParDepartmentsWithEmployeeCount(@PathVariable("company-id")Long companyId) {
    return ResponseEntity.ok(new SingleResponseDto<>(companyService.getParDepartmentsWithEmployeeCount(companyId)));
  }

  @GetMapping("/{company-id}/departments/par/{department-id}/sub")
  public ResponseEntity<?> getSubsidiaryDepartmentsWithEmployeeCount(@PathVariable("company-id")Long companyId, @PathVariable("department-id") Long departmentId) {
    return ResponseEntity.ok(new SingleResponseDto<>(companyService.getSubsidiaryDepartmentsWithEmployeeCount(companyId, departmentId)));
  }

  @GetMapping("/departments/{department-id}/employees")
  public ResponseEntity<?> getEmployeeByDepartmentId(@PathVariable("department-id") Long departmentId) {
    return ResponseEntity.ok(new SingleResponseDto<>(companyService.getEmployeeByDepartmentId(departmentId)));
  }
  @GetMapping("/{company-id}/employees")
  public ResponseEntity<?> getEmployeeNoDepartment(@PathVariable("company-id") Long companyId) {
    return ResponseEntity.ok(new SingleResponseDto<>(companyService.getEmployeeNoDepartment(companyId)));
  }

}
