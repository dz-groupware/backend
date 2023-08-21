package com.example.backend.employee.service;

import com.example.backend.employee.dto.response.EmployeeResDto;
import com.example.backend.employee.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeResDto> findAll() {
        return employeeMapper.findAll();
    }

    @Override
    public EmployeeResDto findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public EmployeeResDto findByLoginId(String loginId) {
        return employeeMapper.findByLoginId(loginId);
    }

    @Override
    public void insert(EmployeeResDto employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void update(EmployeeResDto employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void delete(Long id) {
        employeeMapper.delete(id);
    }

    @Override
    public long getTotalElements() {
        return employeeMapper.getTotalElements();
    }
}
