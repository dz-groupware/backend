package com.example.backend.modal.service;

import com.example.backend.modal.dto.EmpRes;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeRes;

import java.util.List;

public interface ModalService {
    List<ProfileRes> getProfile(Long empId);
    List<TreeRes> getOrgTree(String type, Long empId, Long compId, Long deptId);
    List<EmpRes> getEmpList(String type, Long compId, Long deptId);

}
