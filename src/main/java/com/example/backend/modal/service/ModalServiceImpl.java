package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
      return modalMapper.getProfileByUserId(userId);
    }

    return new ArrayList<>();
  }

  @Override
  public List<TreeItemRes> getOrgTree(String type, Long compId, Long deptId) {
    if(type.equals("basic")) {
      compId = SecurityUtil.getCompanyId();
      return modalMapper.getCompToGnb(compId);
    }
    if (type.equals("comp")) {
      return modalMapper.getGnbToLnb(compId);
    }
    if (type.equals("dept")) {
      return modalMapper.getLnbToLnb(compId, deptId);
    }
    return new ArrayList<TreeItemRes>();
  }


  @Override
  public List<ProfileRes> findEmpList(String type, Long compId, Long deptId) {
    if (type.equals("comp")) {
      return modalMapper.getEmpListByCompId(compId);
    }
    if (type.equals("dept")) {
      return modalMapper.getEmpListByDeptId(compId, deptId);
    }
    return new ArrayList<>();
  }

  @Override
  public SingleResponseDto<?> findOrgResult(String type, String text) {
    log.info(type + text);
    if (type.equals("all")) {
      Map<String, List<?>> result = new HashMap<>();
      Long compId = SecurityUtil.getCompanyId();
      result.put("Tree", modalMapper.findDeptAllByText(compId, text));
      result.put("List", modalMapper.findEmpAllByText(compId, text, text, text, text, text, text, text));
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

  @Override
  public boolean checkEmpIds(Long empId){
    Long userId = SecurityUtil.getUserId();
    List<Long> result = modalMapper.checkEmpIds(userId);
    for (int i=0; i<result.size(); i++){
      if (result.get(i) == empId) {
        return true;
      }
    }
    return false;
  }
}
