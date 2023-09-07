package com.example.backend.modal.mapper;

import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModalMapper {
  List<ProfileRes> getProfileByUserId(Long userId);
  List<TreeItemRes> getCompToGnb(Long compId);
  List<TreeItemRes> getGnbToLnb(Long compId);
  List<TreeItemRes> getLnbToLnb(Long compId, Long deptId);





  List<ProfileRes> findCompEmpList(Long compId);

  List<ProfileRes> findDeptEmpList(Long deptId);

  List<TreeItemRes> findResultOfAllDept(String text);

  List<ProfileRes> findResultOfAllEmp(String text);

  List<TreeItemRes> findResultOfDept(String text);

  List<ProfileRes> findResultOfEmp(String text1, String text2, String text3, String text4);
}
