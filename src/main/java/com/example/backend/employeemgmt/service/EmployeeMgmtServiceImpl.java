package com.example.backend.employeemgmt.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employeemgmt.dto.*;
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

            if (detailList == null || detailList.isEmpty()) {
                // If detailList is null or empty, fetch basic details.
                EmployeeMgmtResDto basicDetails = employeeMgmtMapper.getEmployeeMgmtOnlyBasicDetailsById(empId, companyId);
                if (basicDetails != null) {
                    results.add(basicDetails);
                }
            } else {
                // If detailList is not null and not empty, add its elements to results.
                results.addAll(detailList);
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
        Long userId = null;

        // 해당 사용자의 unique 정보를 가지고 user 테이블에서 확인
        userId = employeeMgmtMapper.getUserIdByUniqueInfo(
                employeeMgmt.getName(), employeeMgmt.getPrivEmail(),
                employeeMgmt.getMobileNumber(), employeeMgmt.getLoginId(), employeeMgmt.getEmpIdNum());

        if (userId == null) {
            // 첫 번째 데이터로 user 테이블에 저장
            employeeMgmtMapper.addEmployeeMgmtUser(employeeMgmt);
            userId = employeeMgmt.getId();

            if (userId == null) {
                throw new RuntimeException("ID 생성 실패");
            }
        }

        Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;
        if (resignedYn) {
            employeeMgmt.setTransferredYn(true);
            employeeMgmt.setLeftDate(new Date());
            employeeMgmt.setAccountYn(false);
        }


        Boolean masterYn = employeeMgmt.getPosition().equals("대표") ? true : false;

        // 사용자 추가 또는 수정에 관련된 나머지 작업들 수행
        employeeMgmtMapper.addEmployeeMgmtEmployee(userId, employeeMgmt, masterYn);
        Long employeeId = employeeMgmt.getId();
        employeeMgmtMapper.addEmployeeMgmtEmployeeCompany(employeeId, employeeMgmt, resignedYn);

        if (masterYn == false) {
            Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;
            employeeMgmtMapper.addEmployeeMgmtEmployeeDepartment(employeeId, employeeMgmt, org);
        }
    }


    @Override
    @Transactional
    public void modifyEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt) {

        Long userId = employeeMgmtMapper.getUserIdById(employeeMgmt.getId());
        employeeMgmt.setDeletedYn(false);

        // departmentId가 null인지 대표인지 확인
        if (employeeMgmt.getDepartmentId() == null && !employeeMgmt.getPosition().equals("대표")) {
            Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;
            // 생성된 ID를 사용하여 직원 추가
            Boolean masterYn = employeeMgmt.getPosition().equals("대표") ? true : false;
            employeeMgmtMapper.addEmployeeMgmtEmployeeModify(userId, employeeMgmt, masterYn);
            Long employeeId = employeeMgmt.getId();
            employeeMgmtMapper.addEmployeeMgmtEmployeeCompany(employeeId, employeeMgmt, resignedYn);
            if (masterYn == false) {
                Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;
                employeeMgmtMapper.addEmployeeMgmtEmployeeDepartment(employeeId, employeeMgmt, org);
            }
            return; // 이후의 코드를 실행하지 않고 메서드를 종료
        }


        List<Long> employeeIds = employeeMgmtMapper.getEmployeeIdsByUserId(userId);


        for (Long empId : employeeIds) {

            Boolean masterYn = employeeMgmt.getPosition().equals("대표") ? true : false;
            if (masterYn == false) {
                Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;
                employeeMgmtMapper.modifyEmployeeMgmtEmployeeDepartment(empId, org, employeeMgmt);
            }
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


                employeeMgmtMapper.removeEmployeeMgmtEmployeeDepartment(empId, employeeMgmt);
            }

            employeeMgmt.setAccountYn(false);
            Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;


            employeeMgmtMapper.removeEmployeeMgmtUser(empId, employeeMgmt);
            employeeMgmtMapper.removeEmployeeMgmtEmployee(empId, employeeMgmt);
            employeeMgmtMapper.removeEmployeeMgmtEmployeeCompany(empId, resignedYn, employeeMgmt);
//
        }
    }

    @Override
    public Boolean checkLoginId(String loginId) {
        String result = employeeMgmtMapper.checkLoginId(loginId);
        return result != null;
    }


    @Override
    @Transactional
    public EmployeeMgmtCheckSignUpResultResDto checkSignUp(EmployeeMgmtSignUpReqDto employeeMgmt) {

        if (employeeMgmtMapper.checkDuplicates(employeeMgmt)) {
                List<EmployeeMgmtListResDto> result = employeeMgmtMapper.checkSignUp(employeeMgmt);
                return new EmployeeMgmtCheckSignUpResultResDto(result, true);
                }
            return new EmployeeMgmtCheckSignUpResultResDto(null, false);
        }


}



