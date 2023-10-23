package com.example.backend.modal.service;

import com.example.backend.common.dto.Page;
import com.example.backend.common.dto.PageDto;
import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.mapper.ModalMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
  public ProfileRes getAllProfile() {
    return modalMapper.getProfileByUserId(SecurityUtil.getEmployeeId());
  }

  @Override
  public List<TreeItemRes> getOrgTree(String type, Long compId, Long deptId) {
    if(type.equals("basic")) {
      return modalMapper.getBasicCompList(SecurityUtil.getCompanyId());
    }
    if (type.equals("comp")) {
      return modalMapper.getCompsUnits(compId);
    }
    if (type.equals("dept")) {
      return modalMapper.getDeptsUnits(compId, deptId);
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
    if (type.equals("all")) {
//      Map<String, List<?>> result = new HashMap<>();
//      result.put("Tree", modalMapper.findDeptAllByText(SecurityUtil.getCompanyId(), text, SecurityUtil.getCompanyId(), text));
//      result.put("List", modalMapper.findEmpAllByText(SecurityUtil.getCompanyId(), text, text, text, text, text, text, text));
      return new SingleResponseDto<>(modalMapper.findEmpAllByText(SecurityUtil.getCompanyId(), text, text, text, text, text, text, text));
    }

    if (type.equals("dept")) {
      return new SingleResponseDto<>(modalMapper.findResultOfDept(text));

//      return new SingleResponseDto<List<TreeItemRes>>(modalMapper.findResultOfDept(text));
    }
    if (type.equals("emp")) {
      return new SingleResponseDto<>(modalMapper.findResultOfEmp(text));
    }
    return new SingleResponseDto<>("");
  }

  @Override
  public boolean checkEmpIds(Long empId){
    List<Long> result = modalMapper.checkEmpIds(SecurityUtil.getUserId());
    for (Long aLong : result) {
      if (Objects.equals(aLong, empId)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Page<ProfileRes> getProfiles(int pageNum){
    Long pageSize = modalMapper.getPageCount(SecurityUtil.getUserId());
    List<ProfileRes> result = modalMapper.getProfilePage(SecurityUtil.getUserId(), (pageNum - 1) * 3);
    return new Page<ProfileRes>(result, new PageDto(pageNum, 3, pageSize));
  }
}
