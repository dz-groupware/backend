package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.PositionRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;
import org.springframework.http.ResponseEntity;
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
    public List<PositionRes> getProfile(Long empId){
        return modalMapper.getProfile(empId);
    }

    @Override
    public List<TreeItemRes> getOrgTree(String type, Long empId, Long compId, Long deptId){
        if (type.equals("comp")){
            return modalMapper.getCompList(empId);
        }
        if (type.equals("Dept1")){
            return modalMapper.getDeptList1(compId);
        }
        if (type.equals("Dept2")){
            return modalMapper.getDeptList2(compId, deptId);
        }
        return new ArrayList<TreeItemRes>();
    }

    @Override
    public List<ProfileRes> getEmpList(String type, Long compId, Long deptId){
        if (type.equals("comp")) {
            return modalMapper.getCompEmpList(compId);
        }
        if (type.equals("dept")) {
            return modalMapper.getDeptEmpList(deptId);
        }
        return new ArrayList<>();
    }

    @Override
    public SingleResponseDto<?> getSearchResult(String type, String text){
        if (type.equals("dept")) {
            System.out.println(type + " : " + text);
            return new SingleResponseDto<List<TreeItemRes>>(modalMapper.searchWithDept(text));
        }
        if (type.equals("emp")) {
            System.out.println(type + " : " + text);
            return new SingleResponseDto<List<ProfileRes>>(modalMapper.searchWithEmp(text, text, text, text));
        }
        return new SingleResponseDto<>("");
    }
}
