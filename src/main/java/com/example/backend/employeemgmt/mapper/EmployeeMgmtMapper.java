package com.example.backend.employeemgmt.mapper;

import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtSignUpReqDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMgmtMapper {
    List<EmployeeMgmtListResDto> getEmployeeMgmtList(Long companyId);

    List<EmployeeMgmtResDto> getEmployeeMgmtDetailsById(@Param("empId")Long empId,@Param("companyId")Long companyId);
    List<Map<Long, String>> getAllDepartmentMgmtList(@Param("companyId")Long comanyId);

    List<EmployeeMgmtListResDto> findEmployeeMgmtList(Long compId, String text);


    List<EmployeeMgmtListResDto> findEmployeeMgmtListByText( Long companyId,String text);

    List<EmployeeMgmtListResDto> findEmployeeMgmtListById(Long compId);
    Long addEmployeeMgmtUser(@Param("dto")EmployeeMgmtReqDto employeeMgmt);

    Long addEmployeeMgmtEmployee(@Param("userId") Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt, @Param("masterYn")Boolean masterYn);

    void addEmployeeMgmtEmployeeCompany(@Param("employeeId") Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt,@Param("resignedYn") Boolean resignedYn);

    void addEmployeeMgmtEmployeeDepartment(@Param("employeeId")Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt, @Param("org")Boolean org);


    void removeEmployeeMgmtUser(@Param("empId")Long id, @Param("dto") EmployeeMgmtReqDto employeeMgmt);

    void removeEmployeeMgmtEmployee(@Param("empId")Long id,@Param("dto") EmployeeMgmtReqDto employeeMgmt);

    void removeEmployeeMgmtEmployeeCompany(@Param("empId")Long id, @Param("resignedYn")Boolean resignedYn, @Param("dto")EmployeeMgmtReqDto employeeMgmt);

    void removeEmployeeMgmtEmployeeDepartment(@Param("empId")Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt);

    Long getUserIdById(Long id);

    List<Long> getEmployeeIdsByUserId(Long userId);



//    modify
    void modifyEmployeeMgmtEmployeeDepartment(@Param("empId")Long id, @Param("org")Boolean org, @Param("dto")EmployeeMgmtReqDto employeeMgmt);

    void modifyEmployeeMgmtUser(@Param("empId")Long id,@Param("dto") EmployeeMgmtReqDto employeeMgmt);

    void modifyEmployeeMgmtEmployee(@Param("empId")Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt);

    void modifyEmployeeMgmtEmployeeCompany(@Param("empId")Long id, @Param("resignedYn")Boolean resignedYn, @Param("dto")EmployeeMgmtReqDto employeeMgmt);

    void addEmployeeMgmtEmployeeModify(@Param("userId")Long userId,  @Param("dto")EmployeeMgmtReqDto employeeMgmt, @Param("masterYn") Boolean masterYn);

    Long getUserIdByUniqueInfo(String name, String privEmail, String mobileNumber, String loginId, String empIdNum);

    String checkLoginId(String loginId);

    List<EmployeeMgmtListResDto> checkSignUp(@Param("dto")EmployeeMgmtSignUpReqDto employeeMgmt);

    Boolean checkDuplicates(@Param("dto")EmployeeMgmtSignUpReqDto employeeMgmt);


    EmployeeMgmtResDto getEmployeeMgmtOnlyBasicDetailsById(Long empId, Long companyId);

    List<EmployeeMgmtListResDto> getIncumbentEmployeeMgmtList(Long companyId);

    List<EmployeeMgmtListResDto> getQuitterEmployeeMgmtList(Long companyId);

    Boolean checkIfCompanyHasCEO(Long companyId);
}
