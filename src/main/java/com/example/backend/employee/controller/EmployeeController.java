package com.example.backend.employee.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.PageDto;
import com.example.backend.common.SingleResponseDto;
import com.example.backend.employee.dto.EmployeeReqDto;
import com.example.backend.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public ResponseEntity findPage(@RequestParam Long companyId, @RequestParam int pageNumber,
      @RequestParam int pageSize) {
    return new ResponseEntity(
        new MultiResponseDto(
            employeeService.findEmployeeList(companyId, pageNumber, pageSize),
            new PageDto(pageNumber, pageSize, employeeService.getTotalElements())
        ), HttpStatus.OK);
  }


  @PostMapping
  public ResponseEntity insert(@RequestBody EmployeeReqDto employee) {
    employeeService.addEmployee(employee);
    return new ResponseEntity<>(new SingleResponseDto<>("성공"),
        HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable Long id, @RequestBody EmployeeReqDto employee) {
    employeeService.modifyEmployee(employee);
    return new ResponseEntity<>(new SingleResponseDto("성공"),
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    employeeService.removeEmployee(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
