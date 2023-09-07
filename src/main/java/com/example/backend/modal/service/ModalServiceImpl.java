package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;

import java.util.HashMap;
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
  public List<ProfileRes> getProfileByUserId() {
    Long userId = SecurityUtil.getUserId();
    return modalMapper.getProfileByUserId(userId);
  }

  @Override
  public List<TreeItemRes> getOrgTree(String type) {
    Long compId = SecurityUtil.getCompanyId();
    if (type.equals("comp")) {
      return modalMapper.getCompToGnb(compId);
    }
    if (type.equals("Dept1")) {
      return modalMapper.getGnbToLnb(compId);
    }
    if (type.equals("Dept2")) {
      Long deptId = SecurityUtil.getDepartmentId();
      return modalMapper.getLnbToLnb(compId, deptId);
    }
    return new ArrayList<TreeItemRes>();
  }



  @Override
  public List<ProfileRes> findEmpList(String type) {
    if (type.equals("comp")) {
      Long compId = SecurityUtil.getCompanyId();
      return modalMapper.findCompEmpList(compId);
    }
    if (type.equals("dept")) {
      Long deptId = SecurityUtil.getDepartmentId();
      return modalMapper.findDeptEmpList(deptId);
    }
    return new ArrayList<>();
  }

  @Override
  public SingleResponseDto<?> findOrgSearchResult(String type, String text) {
    if (type.equals("all")) {
      System.out.println(type + " : " + text);


            List<SingleResponseDto<?>> result = new ArrayList<SingleResponseDto<?>>();
            HashMap<String, List<?>> map = new HashMap<>();

            result.add(new SingleResponseDto<List<TreeItemRes>>(modalMapper.findResultOfAllDept(text)));
            result.add(new SingleResponseDto<List<ProfileRes>>(modalMapper.findResultOfAllEmp(text)));

//            SingleResponseDto result2 = new SingleResponseDto<List<SingleResponseDto>>();
      return new SingleResponseDto<List<SingleResponseDto<?>>>(result);


    }
    if (type.equals("dept")) {
      System.out.println(type + " : " + text);
      return new SingleResponseDto<List<TreeItemRes>>(modalMapper.findResultOfDept(text));
    }
    if (type.equals("emp")) {
      System.out.println(type + " : " + text);
      return new SingleResponseDto<List<ProfileRes>>(
          modalMapper.findResultOfEmp(text, text, text, text));
    }
    return new SingleResponseDto<>("");
  }
}
