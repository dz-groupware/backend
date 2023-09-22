package com.example.backend.employeemgmt.mapper;

import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMgmtMapper {
    List<EmployeeMgmtListResDto> getEmployeeMgmtList(Long companyId);

    List<EmployeeMgmtResDto> getEmployeeMgmtDetailsById(Long id,Long companyId);
    List<Map<Long, String>> getAllDepartmentMgmtList(Long companyId);

    List<EmployeeMgmtListResDto> findEmployeeMgmtList(Long compId, String text);


    List<EmployeeMgmtListResDto> findEmployeeMgmtListByText( Long companyId,String text);

    List<EmployeeMgmtListResDto> findEmployeeMgmtListById(Long compId);
    Long addEmployeeMgmtUser(@Param("dto")EmployeeMgmtReqDto employeeMgmt);

    Long addEmployeeMgmtEmployee(@Param("id") Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt, @Param("masterYn")Boolean masterYn);

    void addEmployeeMgmtEmployeeCompany(@Param("id") Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt,@Param("resignedYn") Boolean resignedYn);

    void addEmployeeMgmtEmployeeDepartment(@Param("id")Long id, @Param("dto")EmployeeMgmtReqDto employeeMgmt, @Param("org")Boolean org);


    void removeEmployeeMgmtEmployee(@Param("id")Long id, @Param("org") Boolean org, @Param("resignedYn")Boolean resignedYn,  @Param("dto")EmployeeMgmtReqDto employeeMgmt);
}
