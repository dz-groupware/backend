package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ModalServiceImpl implements ModalService {

  private final ModalMapper modalMapper;
  private final CheckMapper checkMapper;

  public ModalServiceImpl(ModalMapper modalMapper, CheckMapper checkMapper) {
    this.modalMapper = modalMapper;
    this.checkMapper = checkMapper;

  }

  @Override
  public List<ProfileRes> getAllProfile() {

    Long userId = SecurityUtil.getUserId();
    Long empId = SecurityUtil.getEmployeeId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return modalMapper.getProfileByUserId(userId);
    }

    Long compId = SecurityUtil.getCompanyId();
    Long deptId = SecurityUtil.getDepartmentId();
    List<Long> result = checkMapper.checkExits(userId, empId, deptId, compId);

    if( result.size() == 1 && result.get(0) == 1L ){
      return modalMapper.getProfileForMaster(userId);
    }

    return new ArrayList<>();
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
      return modalMapper.getEmpListByCompId(compId);
    }
    if (type.equals("dept")) {
      Long deptId = SecurityUtil.getDepartmentId();
      return modalMapper.getEmpListByDeptId(deptId);
    }
    return new ArrayList<>();
  }

  @Override
  public SingleResponseDto<?> findOrgResult(String type, String text) {
    if (type.equals("all")) {
      Map<String, List<?>> result = new HashMap<>();
      Long compId = SecurityUtil.getCompanyId();
      result.put("Tree", modalMapper.findDeptAllByText(compId, text));
      result.put("Tree", modalMapper.findEmpAllByText(compId, text, text, text, text, text, text, text));
      return new SingleResponseDto<Map>(result);
    }



    if (type.equals("dept")) {
      return new SingleResponseDto<List<TreeItemRes>>(modalMapper.findResultOfDept(text));
    }
    if (type.equals("emp")) {
      return new SingleResponseDto<List<ProfileRes>>(
          modalMapper.findResultOfEmp(text, text, text, text));
    }
    return new SingleResponseDto<>("");
  }
}
