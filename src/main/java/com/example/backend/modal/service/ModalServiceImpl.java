package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;

import com.example.backend.setting.dto.JwtDto;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public List<ProfileRes> getAllProfile(JwtDto jwtDto) {
    return modalMapper.getProfileByUserId(jwtDto.getUserId());
  }

  @Override
  public List<TreeItemRes> getOrgTree(JwtDto jwtDto, String type, Long deptId) {
    if(type.equals("basic")) {

      return modalMapper.getCompToGnb(jwtDto.getCompId());
    }
    if (type.equals("comp")) {
      return modalMapper.getGnbToLnb(jwtDto.getCompId());
    }
    if (type.equals("dept")) {
      return modalMapper.getLnbToLnb(jwtDto.getCompId(), deptId);
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
  public SingleResponseDto<?> findOrgResult(JwtDto jwtDto, String type, String text) {
    if (type.equals("all")) {
      Map<String, List<?>> result = new HashMap<>();
      result.put("Tree", modalMapper.findDeptAllByText(jwtDto.getCompId(), text, jwtDto.getCompId(), text));
      result.put("List", modalMapper.findEmpAllByText(jwtDto.getCompId(), text, text, text, text, text, text, text));
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
  public boolean checkEmpIds(JwtDto jwtDto, Long empId){
    List<Long> result = modalMapper.checkEmpIds(jwtDto.getUserId());
    for (int i=0; i<result.size(); i++){
      if (result.get(i) == empId) {
        return true;
      }
    }
    return false;
  }
}
