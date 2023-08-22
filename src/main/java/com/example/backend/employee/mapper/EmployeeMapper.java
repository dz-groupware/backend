package com.example.backend.employee.mapper;

import com.example.backend.employee.dto.request.EmpReqDto;
import com.example.backend.employee.dto.response.EmpResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmpReqDto> findEmpList(Long companyId, int offset, int limit);
    EmpResDto findById(Long companyId);
    EmpResDto findByLoginId(String loginId);
    void addEmployee(EmpReqDto employee);
    void modifyEmployee(EmpReqDto employee);
    void removeEmployee(Long id);
    long getTotalElements();
}
