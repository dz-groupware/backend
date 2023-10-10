package com.example.backend.common.service;

import com.example.backend.common.mapper.CommonMapper;
import com.example.backend.config.jwt.PkDto;
import com.example.backend.config.jwt.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class CommonServiceImpl implements CommonService {
  private final CommonMapper commonMapper;
  public CommonServiceImpl(CommonMapper commonMapper) {
    this.commonMapper = commonMapper;
  }

  // 즐겨찾기
  @Override
  public int findFavorById(Long menuId) {
    System.out.println("find : "+ SecurityUtil.getEmployeeId()+" :: "+menuId);
    int check = commonMapper.findFavorById(SecurityUtil.getEmployeeId(), menuId);
    if (check == 0 || check == 1) {
      System.out.println("return: "+ check);
      return check;
    } else {
      System.out.println("error: "+ check);
      // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
      modifyFavorOff(menuId);
      return -1;
    }
  }

  @Override
  public int modifyFavorOn(Long menuId) {
    System.out.println("on : "+SecurityUtil.getEmployeeId()+" :: "+menuId);
    return commonMapper.modifyFavorOn(SecurityUtil.getEmployeeId(), menuId);
  }

  @Override
  public int modifyFavorOff(@RequestHeader Long menuId) {
    System.out.println("off : "+SecurityUtil.getEmployeeId()+" :: "+menuId);
    return commonMapper.modifyFavorOff(SecurityUtil.getEmployeeId(), menuId);
  }

}
