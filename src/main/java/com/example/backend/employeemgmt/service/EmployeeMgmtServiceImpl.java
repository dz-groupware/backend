package com.example.backend.employeemgmt.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import com.example.backend.employeemgmt.mapper.EmployeeMgmtMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeMgmtServiceImpl implements EmployeeMgmtService {

    private final EmployeeMgmtMapper employeeMgmtMapper;

    public EmployeeMgmtServiceImpl(EmployeeMgmtMapper employeeMgmtMapper) {
        this.employeeMgmtMapper = employeeMgmtMapper;
    }

    @Override
    public List<EmployeeMgmtListResDto> getEmployeeMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getEmployeeMgmtList(companyId);
    }

    @Override
    public List<EmployeeMgmtResDto> getEmployeeDetailsById(Long id) {
        Long companyId = SecurityUtil.getCompanyId();

        Long userId = employeeMgmtMapper.getUserIdById(id);
        List<Long> employeeIds = employeeMgmtMapper.getEmployeeIdsByUserId(userId);

        List<EmployeeMgmtResDto> results = new ArrayList<>();


        for (Long empId : employeeIds) {
            List<EmployeeMgmtResDto> detailList = employeeMgmtMapper.getEmployeeMgmtDetailsById(empId, companyId);
            for (EmployeeMgmtResDto data : detailList) {
                results.add(data);
            }
        }

        return results;

    }


    @Override
    public List<Map<Long, String>> getAllDepartmentMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getAllDepartmentMgmtList(companyId);
    }

    @Override
    public List<EmployeeMgmtListResDto> findEmployeeMgmtList(Long compId, String text) {
        Long companyId = SecurityUtil.getCompanyId();
        if ((compId == null || compId <= 0) && (text == null || text.trim().isEmpty())) {
            return employeeMgmtMapper.getEmployeeMgmtList(companyId);
        }
        if (compId == null || compId <= 0) {
            return employeeMgmtMapper.findEmployeeMgmtListByText(companyId, text);
        } else if (text == null || text.trim().isEmpty()) {
            return employeeMgmtMapper.findEmployeeMgmtListById(compId);
        } else {
            // id와 text 둘 다 사용하는 경우
            return employeeMgmtMapper.findEmployeeMgmtList(compId, text);
        }
    }

    @Override
    @Transactional
    public void addEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt) {
        Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;


        //transferredYn 이 바뀌어버려서 resignedYn true일경우로 바꿔서 해야함
        if (resignedYn) {
            employeeMgmt.setTransferredYn(true);
            employeeMgmt.setLeftDate(new Date());
            employeeMgmt.setAccountYn(false);
        }
        // 사용자 추가
        employeeMgmtMapper.addEmployeeMgmtUser(employeeMgmt);

        // 생성된 ID를 employeeMgmt 객체에서 가져옵니다.
        Long generatedId = employeeMgmt.getId();

        if (generatedId == null) {
            throw new RuntimeException("ID 생성 실패");
        }

        // 생성된 ID를 사용하여 직원 추가
        Boolean masterYn = employeeMgmt.getPosition().equals("대표") ? true : false;
        //마스톼이엔 1처리 하는거 없어짐
        employeeMgmtMapper.addEmployeeMgmtEmployee(generatedId, employeeMgmt, masterYn);
        Long employeeId = employeeMgmt.getId();

        // 생성된 ID와 회사 ID를 사용하여 직원-회사 관계 추가
        employeeMgmtMapper.addEmployeeMgmtEmployeeCompany(employeeId, employeeMgmt, resignedYn);

        // 대표가 아닐 경우에만 실행
        if (masterYn == false) {
            Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;
            employeeMgmtMapper.addEmployeeMgmtEmployeeDepartment(employeeId, employeeMgmt, org);
        }
    }

    @Override
    @Transactional
    public void modifyEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt) {
        Long userId = employeeMgmtMapper.getUserIdById(employeeMgmt.getId());
        List<Long> employeeIds = employeeMgmtMapper.getEmployeeIdsByUserId(userId);


        for (Long empId : employeeIds) {

            Boolean masterYn = employeeMgmt.getPosition().equals("대표") ? true : false;
            if (masterYn == false) {
                Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;

                employeeMgmtMapper.modifyEmployeeMgmtEmployeeDepartment(empId, org, employeeMgmt);
            }
            System.out.println("ddddddddddddddddddddddddddddddddddddddddddd");
            Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;
            employeeMgmt.setLeftDate(employeeMgmt.getResignationDate());
            employeeMgmtMapper.modifyEmployeeMgmtUser(empId, employeeMgmt);
            employeeMgmtMapper.modifyEmployeeMgmtEmployee(empId, employeeMgmt);
            employeeMgmtMapper.modifyEmployeeMgmtEmployeeCompany(empId, resignedYn, employeeMgmt);
        }

    }

    @Override
    @Transactional
    public void removeEmployeeMgmt(Long id, EmployeeMgmtReqDto employeeMgmt) {

        Long userId = employeeMgmtMapper.getUserIdById(id);
        List<Long> employeeIds = employeeMgmtMapper.getEmployeeIdsByUserId(userId);

        for (Long empId : employeeIds) {
            employeeMgmt.setDeletedYn(true);
// 대표가 아닐 때만 removeEmployeeMgmtEmployeeDepartment 매퍼를 호출
            if (!"대표".equals(employeeMgmt.getPosition())) {
                employeeMgmt.setTransferredYn(true);
                Boolean org = employeeMgmt.getTransferredYn() == false ? true : false;

                // leftDate가 없는 경우 getResignationDate로 설정
                if (employeeMgmt.getLeftDate() == null) {
                    employeeMgmt.setLeftDate(employeeMgmt.getResignationDate());
                }

                employeeMgmtMapper.removeEmployeeMgmtEmployeeDepartment(empId, org, employeeMgmt);
            }

            employeeMgmt.setAccountYn(false);
            Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;



            employeeMgmtMapper.removeEmployeeMgmtUser(empId, employeeMgmt);
            employeeMgmtMapper.removeEmployeeMgmtEmployee(empId, employeeMgmt);
            employeeMgmtMapper.removeEmployeeMgmtEmployeeCompany(empId, resignedYn, employeeMgmt);
//
        }
    }
}



