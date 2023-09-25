package com.example.backend.modal.service;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;

import com.example.backend.common.dto.PkDto;
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
  public List<ProfileRes> getAllProfile(PkDto pkDto) {
    return modalMapper.getProfileByUserId(pkDto.getUserId());
  }

  @Override
  public List<TreeItemRes> getOrgTree(PkDto pkDto, String type, Long deptId) {
    if(type.equals("basic")) {

      return modalMapper.getCompToGnb(pkDto.getCompId());
    }
    if (type.equals("comp")) {
      return modalMapper.getGnbToLnb(pkDto.getCompId());
    }
    if (type.equals("dept")) {
      return modalMapper.getLnbToLnb(pkDto.getCompId(), deptId);
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
  public SingleResponseDto<?> findOrgResult(PkDto pkDto, String type, String text) {
    if (type.equals("all")) {
      Map<String, List<?>> result = new HashMap<>();
      result.put("Tree", modalMapper.findDeptAllByText(pkDto.getCompId(), text, pkDto.getCompId(), text));
      result.put("List", modalMapper.findEmpAllByText(pkDto.getCompId(), text, text, text, text, text, text, text));
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
  public boolean checkEmpIds(PkDto pkDto, Long empId){
    List<Long> result = modalMapper.checkEmpIds(pkDto.getUserId());
    for (int i=0; i<result.size(); i++){
      if (result.get(i) == empId) {
        return true;
      }
    }
    return false;
  }
}
