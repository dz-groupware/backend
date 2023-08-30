package com.example.backend.modal.mapper;

import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.PositionRes;
import com.example.backend.modal.dto.TreeItemRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModalMapper {
    List<PositionRes> getProfile(Long empId);
    List<TreeItemRes> getCompList(Long empId);
    List<TreeItemRes> getDeptList1(Long compId);
    List<TreeItemRes> getDeptList2(Long compId, Long deptId);
    List<ProfileRes> getCompEmpList(Long compId);
    List<ProfileRes> getDeptEmpList(Long deptId);
    List<TreeItemRes> searchWithDept(String text);
    List<ProfileRes> searchWithEmp(String text1, String text2, String text3, String text4);
}
