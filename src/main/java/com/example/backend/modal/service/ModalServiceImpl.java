package com.example.backend.modal.service;

import com.example.backend.modal.dto.EmpRes;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeRes;
import com.example.backend.modal.mapper.ModalMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModalServiceImpl implements ModalService {
    private final ModalMapper modalMapper;

    public ModalServiceImpl(ModalMapper modalMapper) {
        this.modalMapper = modalMapper;
    }

    @Override
    public List<ProfileRes> getProfile(Long empId){
        return modalMapper.getProfile(empId);
    }

    @Override
    public List<TreeRes> getOrgTree(String type, Long empId, Long compId, Long deptId){
        if (type.equals("comp")){
            return modalMapper.getCompList(empId);
        }
        if (type.equals("Dept1")){
            return modalMapper.getDeptList1(empId);
        }
        if (type.equals("Dept2")){
            return modalMapper.getDeptList2(compId, deptId);
        }
        return new ArrayList<TreeRes>();
    }

    @Override
    public List<EmpRes> getEmpList(String type, Long compId, Long deptId){
        if (type.equals("comp")) {
            return modalMapper.getCompEmpList(compId);
        }
        if (type.equals("dept")) {
            return modalMapper.getDeptEmpList(deptId);
        }
        return new ArrayList<>();
    }

}
