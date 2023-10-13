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
    public List<EmployeeMgmtListResDto> getIncumbentEmployeeMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getIncumbentEmployeeMgmtList(companyId);
    }
    @Override
    public List<EmployeeMgmtListResDto> getQuitterEmployeeMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getQuitterEmployeeMgmtList(companyId);
    }


    @Override
    public List<EmployeeMgmtResDto> getEmployeeDetailsById(Long id) {
        Long companyId = SecurityUtil.getCompanyId();

        Long userId = employeeMgmtMapper.getUserIdById(id);
        List<Long> employeeIds = employeeMgmtMapper.getEmployeeIdsByUserId(userId);

        List<EmployeeMgmtResDto> results = new ArrayList<>();

        for (Long empId : employeeIds) {
            System.out.println("empId ??"+empId);
            Long masteryn= employeeMgmtMapper.getMasterYn(empId);
            System.out.println("what is masteryn"+masteryn);
            List<EmployeeMgmtResDto> detailList = employeeMgmtMapper.getEmployeeMgmtDetailsById(empId, companyId);

            if ((detailList == null || detailList.isEmpty())&& masteryn != null && masteryn==1   ) {
                // If detailList is null or empty, fetch basic details.
                EmployeeMgmtResDto basicDetails = employeeMgmtMapper.getEmployeeMgmtOnlyBasicDetailsById(empId, companyId);
                if (basicDetails != null) {
                    results.add(basicDetails);
                }
            } else {
                results.addAll(detailList);
            }
        }

        return results;
    }

    @Override
    public List<Map<Long, String>> getAllDepartmentMgmtList(Long companyId) {
        return employeeMgmtMapper.getAllDepartmentMgmtList(companyId);
    }

    @Override
    public List<EmployeeMgmtListResDto> findEmployeeMgmtList(Long deptId, String text) {
        Long companyId = SecurityUtil.getCompanyId();
        System.out.println("Did u come here?1"+companyId);
        System.out.println("deptId"+deptId);
        System.out.println("text"+text);



        if ((deptId == null || deptId <= 0) && (text == null || text.trim().isEmpty() || "%%".equals(text))) {

                System.out.println("Did u come here?2");
            return employeeMgmtMapper.getEmployeeMgmtList(companyId);
        }
        if (deptId == null || deptId <= 0) {
            return employeeMgmtMapper.findEmployeeMgmtListByText(companyId, text);
        } else if (text == null || text.trim().isEmpty()) {
            return employeeMgmtMapper.findEmployeeMgmtListById(deptId);
        } else {
            // id와 text 둘 다 사용하는 경우
            return employeeMgmtMapper.findEmployeeMgmtList(deptId, text);
        }
    }
    @Override
    public List<EmployeeMgmtListResDto> findOpenEmployeeMgmtList(Long deptId, String text) {
        Long companyId = SecurityUtil.getCompanyId();
        if ((deptId == null || deptId <= 0) && (text == null || text.trim().isEmpty() || "%%".equals(text))) {

                return employeeMgmtMapper.getEmployeeMgmtList(companyId);
        }
        if (deptId == null || deptId <= 0) {
            return employeeMgmtMapper.findOpenEmployeeMgmtListByText(companyId, text);
        } else if (text == null || text.trim().isEmpty()) {
            return employeeMgmtMapper.findOpenEmployeeMgmtListById(deptId);
        } else {
            // id와 text 둘 다 사용하는 경우
            return employeeMgmtMapper.findOpenEmployeeMgmtList(deptId, text);
        }
    }
    @Override
    public List<EmployeeMgmtListResDto> findCloseEmployeeMgmtList(Long deptId, String text) {
        Long companyId = SecurityUtil.getCompanyId();
        if ((deptId == null || deptId <= 0) && (text == null || text.trim().isEmpty())) {
            return employeeMgmtMapper.getEmployeeMgmtList(companyId);
        }
        if (deptId == null || deptId <= 0) {
            return employeeMgmtMapper.findCloseEmployeeMgmtListByText(companyId, text);
        } else if (text == null || text.trim().isEmpty()) {
            return employeeMgmtMapper.findCloseEmployeeMgmtListById(deptId);
        } else {
            // id와 text 둘 다 사용하는 경우
            return employeeMgmtMapper.findCloseEmployeeMgmtList(deptId, text);
        }
    }

    @Override
    public List<Map<Long, String>> getDepartmentList() {
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getAllDepartmentMgmtList(companyId);
    }


    @Override
    @Transactional
    public void addEmployeeMgmt(EmployeeMgmtReqDto employeeMgmt) {
        Long userId = null;

        if(employeeMgmt.getImageUrl()==null || employeeMgmt.getImageUrl()==""){
            employeeMgmt.setImageUrl("https://img.freepik.com/free-vector/young-businessman-showing-ok-sign-hand-drawn-cartoon-art-illustration_56104-1093.jpg?size=626&ext=jpg&ga=GA1.1.2127282484.1692942479&semt=sph");
        }

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
        if(employeeMgmt.getImageUrl()==null || employeeMgmt.getImageUrl()==""){
            employeeMgmt.setImageUrl("https://img.freepik.com/free-vector/young-businessman-showing-ok-sign-hand-drawn-cartoon-art-illustration_56104-1093.jpg?size=626&ext=jpg&ga=GA1.1.2127282484.1692942479&semt=sph");
        }

        Long userId = employeeMgmtMapper.getUserIdById(employeeMgmt.getId());

        System.out.println("deletedyn what?"+employeeMgmt.getDeletedYn());
        System.out.println("employeeMgmt"+employeeMgmt.toString());
        // departmentId가 null인지 대표인지 확인



        if (employeeMgmt.getPosition().equals("대표")) {
            Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;
            // 생성된 ID를 사용하여 직원 추가
            Boolean masterYn = employeeMgmt.getPosition().equals("대표") ? true : false;
            employeeMgmtMapper.addEmployeeMgmtEmployeeModify(userId, employeeMgmt, masterYn);
            Long employeeId = employeeMgmt.getId();
            employeeMgmtMapper.addEmployeeMgmtEmployeeCompany(employeeId, employeeMgmt, resignedYn);
            return; // 이후의 코드를 실행하지 않고 메서드를 종료
        }

        //부서가 없을때
        if (employeeMgmt.getDepartmentId() == null ) {
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

        }
    }

    @Override
    public Boolean checkLoginId(String loginId) {
        String result = employeeMgmtMapper.checkLoginId(loginId);
        if (result != null) {
            return true;
        } else {
            return false;
        }
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

    @Override
    public Boolean checkIfCompanyHasCEO(Long companyId) {
        Boolean result = employeeMgmtMapper.checkIfCompanyHasCEO(companyId);
        System.out.println(result);
        return result;
    }


}



