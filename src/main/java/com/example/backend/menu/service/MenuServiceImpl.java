package com.example.backend.menu.service;

import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import com.example.backend.config.jwt.PkDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;
  private final CheckMapper checkMapper;

  public MenuServiceImpl(MenuMapper menuMapper, CheckMapper checkMapper) {
    this.menuMapper = menuMapper;
    this.checkMapper = checkMapper;
  }

  @Override
  public List<MenuDto> getGnbById(PkDto pkDto) {
    if(checkMapper.checkMaster(pkDto.getEmpId())) {
      // 마스터인 경우
      return menuMapper.getGnbForMaster(pkDto.getCompId());
    } else {
      return menuMapper.getGnbByEmpId(pkDto.getEmpId(), pkDto.getCompId(), pkDto.getDeptId());
    }
  }

  @Override
  public List<MenuDto> getFavorByEmpId(PkDto pkDto) {
    Long empId = pkDto.getEmpId();
    Long compId = pkDto.getCompId();
    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getFavorForMaster(compId);
    } else {
      return menuMapper.getFavorByEmpId(empId, compId, pkDto.getDeptId());
    }
  }

  @Override
  public int removeFavor(PkDto pkDto, Long menuId) {
    return menuMapper.removeFavor(pkDto.getEmpId(), menuId);
  }

  @Override
  public List<MenuDto> getMenuById(PkDto pkDto, Long menuId) {
    Long empId = pkDto.getEmpId();
    Long compId = pkDto.getCompId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getMenuForMaster(menuId, compId);
    } else {
      return menuMapper.getMenuById(menuId, empId, compId, pkDto.getDeptId());
    }
  }

  @Override
  public List<MenuDto> getUpperMenuGnb(PkDto pkDto) {
    return menuMapper.getUpperMenuGnb(pkDto.getCompId());
  }

  @Override
  public List<MenuDto> getUpperMenuLnb(PkDto pkDto, Long menuId) {
    return menuMapper.getUpperMenuLnb(menuId, pkDto.getCompId());
  }
}
