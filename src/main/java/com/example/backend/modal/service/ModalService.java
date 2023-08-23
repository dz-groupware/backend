package com.example.backend.modal.service;

import com.example.backend.menu.dto.MenuRes;
import com.example.backend.modal.dto.EmpRes;
import com.example.backend.modal.dto.ModalRes;
import com.example.backend.modal.dto.TreeRes;

import java.util.List;

public interface ModalService {
    List<ModalRes> getProfile(Long empId);
    List<TreeRes> getOrgTree(String type, Long empId, Long compId, Long deptId);
    List<EmpRes> getEmpList(String type, Long compId, Long deptId);

}
