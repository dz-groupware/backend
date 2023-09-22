package com.example.backend.employeemgmt.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import com.example.backend.employeemgmt.mapper.EmployeeMgmtMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public  List<EmployeeMgmtResDto> getEmployeeDetailsById(Long id){
        Long companyId = SecurityUtil.getCompanyId();
        return employeeMgmtMapper.getEmployeeMgmtDetailsById(id, companyId);
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
            return employeeMgmtMapper.findEmployeeMgmtListByText(companyId,text);
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
        Long companyId = SecurityUtil.getCompanyId();
        Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;

        if (resignedYn) {
            employeeMgmt.setTransferredYn(true);
            employeeMgmt.setLeftDate(new Date());
            employeeMgmt.setAccountYn(false);
        }
        // 사용자 추가
        employeeMgmtMapper.addEmployeeMgmtUser(employeeMgmt);

        // 생성된 ID를 employeeMgmt 객체에서 가져옵니다.
        Long generatedId = employeeMgmt.getId();

        if(generatedId == null) {
            throw new RuntimeException("ID 생성 실패");
        }

        // 생성된 ID를 사용하여 직원 추가
        Boolean masterYn = employeeMgmt.getPosition().equals("대표");
        employeeMgmtMapper.addEmployeeMgmtEmployee(generatedId, employeeMgmt, masterYn);
        Long employeeId = employeeMgmt.getId();

        // 생성된 ID와 회사 ID를 사용하여 직원-회사 관계 추가
        employeeMgmtMapper.addEmployeeMgmtEmployeeCompany(employeeId, employeeMgmt, resignedYn);

        // 대표가 아닐 경우에만 실행
        if (!masterYn) {
            Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;
            employeeMgmtMapper.addEmployeeMgmtEmployeeDepartment(employeeId, employeeMgmt, org);
        }
    }

    @Override
    public void removeEmployeeMgmt(Long id, EmployeeMgmtReqDto employeeMgmt) {

        employeeMgmt.setAccountYn(false);
        Boolean resignedYn = employeeMgmt.getResignationDate() == null ? false : true;
        Boolean org = employeeMgmt.getTransferredYn() == true ? false : true;
        employeeMgmt.setDeletedYn(true);
        employeeMgmtMapper.removeEmployeeMgmtEmployee(id,org,resignedYn,employeeMgmt);


    }


}
