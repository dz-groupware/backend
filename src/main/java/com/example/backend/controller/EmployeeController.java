package com.example.backend.controller;

import com.example.backend.model.EmployeeDto;
import com.example.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public void insert(@RequestBody EmployeeDto employee) {
        employeeService.insert(employee);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody EmployeeDto employee) {
        employeeService.update(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
