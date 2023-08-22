package com.example.backend.employee.service;

import com.example.backend.employee.dto.request.EmpReqDto;
import com.example.backend.employee.dto.response.EmpResDto;
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
    public List<EmpReqDto> findEmpList(Long companyId, int pageNumber, int pageSize) {
        int offset = (pageNumber-1) * pageSize;
        return employeeMapper.findEmpList(companyId, offset, pageSize);
    }

    @Override
    public EmpResDto findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public EmpResDto findByLoginId(String loginId) {
        return employeeMapper.findByLoginId(loginId);
    }

    @Override
    public void addEmployee(EmpReqDto employee) {
        employeeMapper.addEmployee(employee);
    }

    @Override
    public void modifyEmployee(EmpReqDto employee) {
        employeeMapper.modifyEmployee(employee);
    }

    @Override
    public void removeEmployee(Long id) {
        employeeMapper.removeEmployee(id);
    }

    @Override
    public long getTotalElements() {
        return employeeMapper.getTotalElements();
    }
}
