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
  List<ProfileRes> getEmpListByCompId(Long compId);
  List<ProfileRes> getEmpListByDeptId(Long compId, Long deptId);
  List<TreeItemRes> findDeptAllByText(Long compId1, String text1, Long compId2, String text2);
  List<ProfileRes> findEmpAllByText(Long compId, String text1, String text2, String text3, String text4, String text5, String text6, String text7);
  Long getPageCount(Long userId);
  List<ProfileRes> getProfilePage(Long userId, int pageNum);


  List<TreeItemRes> findResultOfDept(String text);

  List<ProfileRes> findResultOfEmp(String text);

  List<Long> checkEmpIds(Long userId);
}
