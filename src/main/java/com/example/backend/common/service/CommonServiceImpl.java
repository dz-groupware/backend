package com.example.backend.common.service;

import com.example.backend.common.mapper.CommonMapper;
import com.example.backend.config.jwt.PkDto;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
  private final CommonMapper commonMapper;
  public CommonServiceImpl(CommonMapper commonMapper) {
    this.commonMapper = commonMapper;
  }

  // 즐겨찾기
  @Override
  public String findFavorById(PkDto pkDto, Long menuId) {
    int check = commonMapper.findFavorById(pkDto.getEmpId(), menuId);
    if (check == 0) {
      return "false";
    }
    if (check == 1) {
      return "true";
    }
    // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
    modifyFavorOff(pkDto, menuId);
    return "false";
  }

  @Override
  public int modifyFavorOn(PkDto pkDto, Long menuId) {
    return commonMapper.modifyFavorOn(pkDto.getEmpId(), menuId);
  }

  @Override
  public int modifyFavorOff(PkDto pkDto, Long menuId) {
    return commonMapper.modifyFavorOff(pkDto.getEmpId(), menuId);
  }

}
