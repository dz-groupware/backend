package com.example.backend.employee.service;

import com.example.backend.employee.dto.response.EmployeeResDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResDto> findAll();
    EmployeeResDto findById(Long id);
    EmployeeResDto findByLoginId(String loginId);
    void insert(EmployeeResDto employee);
    void update(EmployeeResDto employee);
    void delete(Long id);
    long getTotalElements();
}
