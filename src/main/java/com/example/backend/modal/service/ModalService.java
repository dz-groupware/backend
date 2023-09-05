package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.PositionRes;
import com.example.backend.modal.dto.TreeItemRes;

import java.util.List;

public interface ModalService {
    List<PositionRes> findProfileByEmpId(Long empId);
    List<TreeItemRes> findOrgTree(String type, Long empId, Long compId, Long deptId);
    List<ProfileRes> findEmpList(String type, Long compId, Long deptId);
    SingleResponseDto<?> findOrgSearchResult(String type, String text);
}
