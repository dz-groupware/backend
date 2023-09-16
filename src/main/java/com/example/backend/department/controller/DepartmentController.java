package com.example.backend.department.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.service.DepartmentService;
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

  @GetMapping("")
  public ResponseEntity<?> getDepartment() {
    Long compId = SecurityUtil.getCompanyId();
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getDepartmentBasicList(compId)), HttpStatus.OK);
  }

  @PostMapping("/dept")
  public ResponseEntity<?> addDepartment(@RequestBody DeptDto dept) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.addDepartment(dept)), HttpStatus.OK);
  }

  @GetMapping("dept-list")
  public ResponseEntity<?> getDepartmentList(@RequestParam Long parId) {
    Long compId = SecurityUtil.getCompanyId();
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getDepartmentById(compId, parId)), HttpStatus.OK);
  }

  @GetMapping("detail-basic")
  public ResponseEntity<?> getBasicDetailById(@RequestParam Long id) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getBasicDetailById(id)), HttpStatus.OK);
  }


}