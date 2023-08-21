package com.example.backend.mapper;

import com.example.backend.model.EmployeeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDto> findAll();
    EmployeeDto findById(Long id);
    EmployeeDto findByLoginId(String loginId);
    void insert(EmployeeDto employee);
    void update(EmployeeDto employee);
    void delete(Long id);
}
