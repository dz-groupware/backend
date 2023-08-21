package com.example.backend.service;

import com.example.backend.model.EmployeeDto;
import com.example.backend.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeMapper.findAll();
    }

    @Override
    public EmployeeDto findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public EmployeeDto findByLoginId(String loginId) {
        return employeeMapper.findByLoginId(loginId);
    }

    @Override
    public void insert(EmployeeDto employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void update(EmployeeDto employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void delete(Long id) {
        employeeMapper.delete(id);
    }
}
