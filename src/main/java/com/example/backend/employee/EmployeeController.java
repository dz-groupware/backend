package com.example.backend.employee;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.employee.dto.EmployeeMDto;
import com.example.backend.employee.dto.UpdateMasterYnRequest;
import com.example.backend.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PatchMapping("/master")
  public ResponseEntity<?> changeMasterYnOfEmp(@RequestBody UpdateMasterYnRequest updateMasterYnRequest){
    EmployeeMDto result = employeeService.changeMasterYn(updateMasterYnRequest);
    return ResponseEntity.ok(new SingleResponseDto<>(result));
  }
}
