package com.example.backend.common.service;

import com.example.backend.common.mapper.CommonMapper;
import com.example.backend.config.jwt.PkDto;
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
  public int findFavorById(PkDto pkDto, Long menuId) {
    System.out.println("find : "+pkDto.getEmpId()+" :: "+menuId);
    int check = commonMapper.findFavorById(pkDto.getEmpId(), menuId);
    if (check == 0 || check == 1) {
      System.out.println("return: "+ check);
      return check;
    } else {
      System.out.println("error: "+ check);
      // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
      modifyFavorOff(pkDto, menuId);
      return -1;
    }
  }

  @Override
  public int modifyFavorOn(PkDto pkDto, Long menuId) {
    System.out.println("on : "+pkDto.getEmpId()+" :: "+menuId);
    return commonMapper.modifyFavorOn(pkDto.getEmpId(), menuId);
  }

  @Override
  public int modifyFavorOff(PkDto pkDto,@RequestHeader Long menuId) {
    System.out.println("off : "+pkDto.getEmpId()+" :: "+menuId);
    return commonMapper.modifyFavorOff(pkDto.getEmpId(), menuId);
  }

}
