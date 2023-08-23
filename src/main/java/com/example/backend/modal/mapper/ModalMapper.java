package com.example.backend.modal.mapper;

import com.example.backend.modal.dto.EmpRes;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModalMapper {
    List<ProfileRes> getProfile(Long empId);
    List<TreeRes> getCompList(Long empId);
    List<TreeRes> getDeptList1(Long compId);
    List<TreeRes> getDeptList2(Long compId, Long deptId);
    List<EmpRes> getCompEmpList(Long compId);
    List<EmpRes> getDeptEmpList(Long deptId);

}
