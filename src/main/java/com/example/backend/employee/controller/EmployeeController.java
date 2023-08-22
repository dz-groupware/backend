package com.example.backend.employee.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.PageDto;
import com.example.backend.common.SingleResponseDto;
import com.example.backend.employee.dto.request.EmpReqDto;
import com.example.backend.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity findPage(@RequestParam Long companyId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return new ResponseEntity(new MultiResponseDto(employeeService.findEmpList(companyId, pageNumber, pageSize),new PageDto(pageNumber, pageSize, employeeService.getTotalElements())), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return new ResponseEntity<>(new SingleResponseDto<>(employeeService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody EmpReqDto employee) {
        employeeService.addEmployee(employee);
        return new ResponseEntity<>(new SingleResponseDto<>(new SingleResponseDto("성공")), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody EmpReqDto employee) {
        employeeService.modifyEmployee(employee);
        return new ResponseEntity<>( new SingleResponseDto("성공"), HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        employeeService.removeEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
