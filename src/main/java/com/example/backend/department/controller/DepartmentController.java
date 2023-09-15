package com.example.backend.department.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.PageDto;
import com.example.backend.common.SingleResponseDto;
import com.example.backend.department.dto.DeptRes;
import com.example.backend.department.service.DepartmentService;
import com.example.backend.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/department")
public class DepartmentController {

  private final DepartmentService departmentService;

  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping
  public ResponseEntity<?> getDepartment() {
    departmentService.getDepartment();
    return new ResponseEntity(
        new SingleResponseDto<>(""), HttpStatus.OK);
  }

  @PostMapping("/dept")
  public ResponseEntity<?> addDepartment(@RequestBody DeptRes dept) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.addDepartment(dept)), HttpStatus.OK);
  }
}