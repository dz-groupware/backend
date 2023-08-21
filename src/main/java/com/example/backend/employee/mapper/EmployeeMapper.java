package com.example.backend.employee.mapper;

import com.example.backend.employee.dto.response.EmployeeResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeResDto> findAll();
    EmployeeResDto findById(Long id);
    EmployeeResDto findByLoginId(String loginId);
    void insert(EmployeeResDto employee);
    void update(EmployeeResDto employee);
    void delete(Long id);
    long getTotalElements();
}
