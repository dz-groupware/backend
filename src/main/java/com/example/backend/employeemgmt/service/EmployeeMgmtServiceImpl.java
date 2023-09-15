package com.example.backend.employeemgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employee.mapper.EmployeeMapper;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import com.example.backend.employeemgmt.mapper.EmployeeMgmtMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeMgmtServiceImpl implements EmployeeMgmtService{

    private final EmployeeMgmtMapper employeeMgmtMapper;
    public EmployeeMgmtServiceImpl(EmployeeMgmtMapper employeeMgmtMapper){
        this.employeeMgmtMapper = employeeMgmtMapper;
    }

    @Override
    public List<EmployeeMgmtListResDto> getEmployeeMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getEmployeeMgmtList(companyId);
    }
}
