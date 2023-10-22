package com.example.backend.modal.service;

import com.example.backend.common.dto.Page;
import com.example.backend.common.dto.PageDto;
import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.config.jwt.PkDto;
import java.util.List;

public interface ModalService {
  ProfileRes getAllProfile();
  List<TreeItemRes> getOrgTree(String type, Long compId, Long deptId);

  List<ProfileRes> findEmpList(String type, Long compId, Long deptId);

  SingleResponseDto<?> findOrgResult(String type, String text);

  boolean checkEmpIds(Long empId);
  Page<ProfileRes> getProfiles(int pageNum);
}
