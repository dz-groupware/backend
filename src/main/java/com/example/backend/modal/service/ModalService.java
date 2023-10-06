package com.example.backend.modal.service;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.config.jwt.PkDto;
import java.util.List;

public interface ModalService {
  List<ProfileRes> getAllProfile();
  List<TreeItemRes> getOrgTree(PkDto pkDto, String type, Long deptId);

  List<ProfileRes> findEmpList(String type, Long compId, Long deptId);

  SingleResponseDto<?> findOrgResult(PkDto pkDto, String type, String text);

  boolean checkEmpIds(PkDto pkDto, Long empId);
}
