package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.setting.dto.JwtDto;
import java.util.List;

public interface ModalService {
  List<ProfileRes> getAllProfile(JwtDto jwtDto);
  List<TreeItemRes> getOrgTree(JwtDto jwtDto, String type, Long deptId);

  List<ProfileRes> findEmpList(String type, Long compId, Long deptId);

  SingleResponseDto<?> findOrgResult(JwtDto jwtDto, String type, String text);

  boolean checkEmpIds(JwtDto jwtDto, Long empId);
}
