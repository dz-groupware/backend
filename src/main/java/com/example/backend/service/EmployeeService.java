package com.example.backend.service;

import com.example.backend.model.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    EmployeeDto findById(Long id);
    EmployeeDto findByLoginId(String loginId);
    void insert(EmployeeDto employee);
    void update(EmployeeDto employee);
    void delete(Long id);
}
