package com.example.backend.modal.mapper;

import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.PositionRes;
import com.example.backend.modal.dto.TreeItemRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModalMapper {
    List<PositionRes> findProfileByEmpId(Long empId);
    List<TreeItemRes> findCompList(Long empId);
    List<TreeItemRes> findDeptList1(Long compId);
    List<TreeItemRes> findDeptList2(Long compId, Long deptId);
    List<ProfileRes> findCompEmpList(Long compId);
    List<ProfileRes> findDeptEmpList(Long deptId);

    List<TreeItemRes> findResultOfAllDept(String text);
    List<ProfileRes> findResultOfAllEmp(String text);
    List<TreeItemRes> findResultOfDept(String text);
    List<ProfileRes> findResultOfEmp(String text1, String text2, String text3, String text4);
}
