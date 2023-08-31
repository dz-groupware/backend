package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.PositionRes;
import com.example.backend.modal.dto.TreeItemRes;
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
    public List<PositionRes> findProfileByEmpId(Long empId){
        return modalMapper.findProfileByEmpId(empId);
    }

    @Override
    public List<TreeItemRes> findOrgTree(String type, Long empId, Long compId, Long deptId){
        if (type.equals("comp")){
            return modalMapper.findCompList(empId);
        }
        if (type.equals("Dept1")){
            return modalMapper.findDeptList1(compId);
        }
        if (type.equals("Dept2")){
            return modalMapper.findDeptList2(compId, deptId);
        }
        return new ArrayList<TreeItemRes>();
    }

    @Override
    public List<ProfileRes> findEmpList(String type, Long compId, Long deptId){
        if (type.equals("comp")) {
            return modalMapper.findCompEmpList(compId);
        }
        if (type.equals("dept")) {
            return modalMapper.findDeptEmpList(deptId);
        }
        return new ArrayList<>();
    }

    @Override
    public SingleResponseDto<?> findOrgSearchResult(String type, String text){
        if (type.equals("dept")) {
            System.out.println(type + " : " + text);
            return new SingleResponseDto<List<TreeItemRes>>(modalMapper.findResultWithDept(text));
        }
        if (type.equals("emp")) {
            System.out.println(type + " : " + text);
            return new SingleResponseDto<List<ProfileRes>>(modalMapper.findResultWithEmp(text, text, text, text));
        }
        return new SingleResponseDto<>("");
    }
}
