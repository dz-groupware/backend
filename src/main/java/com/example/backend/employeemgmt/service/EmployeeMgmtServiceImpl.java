//package com.example.backend.employeemgmt.service;
//
//import com.example.backend.employee.mapper.EmployeeMapper;
//import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
//import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
//import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
//import com.example.backend.employeemgmt.mapper.EmployeeMgmtMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmployeeMgmtServiceImpl implements EmployeeMgmtService{
//
//    private final EmployeeMgmtMapper employeeMgmtMapper;
//    public EmployeeMgmtServiceImpl(EmployeeMgmtMapper employeeMgmtMapper){
//        this.employeeMgmtMapper = employeeMgmtMapper;
//    }
//    @Override
//    public List<EmployeeMgmtListResDto> getEmployeeMgmtList() {
//        return employeeMgmtMapper.getEmployeeMgmtList();
//    }
//
//    @Override
//    public EmployeeMgmtResDto getEmployeeDetailsById(Long id) {
//        return employeeMgmtMapper.getEmployeeDetailsById(id);
//    }
//
//    @Override
//    public List<EmployeeMgmtListResDto> findEmployeeMgmtList(String compId, String searchType) {
//        return employeeMgmtMapper.findEmployeeMgmtList(compId,searchType);
//    }
//
//    @Override
//    public void addEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt) {
//        employeeMgmtMapper.addEmployeeMgmt(employeeMgmt);
//    }
//
//    @Override
//    public void modifyEmployeeMgmt(Long id, EmployeeMgmtReqDto employeeMgmt) {
//        employeeMgmt.setId(id);
//        employeeMgmtMapper.modifyEmployeeMgmt(employeeMgmt);
//    }
//
//    @Override
//    public void removeEmployeeMgmt(Long id) {
//        employeeMgmtMapper.removeEmployeeMgmt(id);
//    }
//}
