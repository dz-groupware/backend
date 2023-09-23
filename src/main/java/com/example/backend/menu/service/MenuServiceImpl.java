package com.example.backend.menu.service;

import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import com.example.backend.setting.dto.JwtDto;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public List<MenuDto> getGnbById(JwtDto jwtDto) {
    if(checkMapper.checkMaster(jwtDto.getEmpId())) {
      // 마스터인 경우
      return menuMapper.getGnbForMaster(jwtDto.getCompId());
    } else {
      return menuMapper.getGnbByEmpId(jwtDto.getEmpId(), jwtDto.getCompId(), jwtDto.getDeptId());
    }
  }

  @Override
  public List<MenuDto> getFavorByEmpId(JwtDto jwtDto) {
    Long empId = jwtDto.getEmpId();
    Long compId = jwtDto.getCompId();
    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getFavorForMaster(compId);
    } else {
      return menuMapper.getFavorByEmpId(empId, compId, jwtDto.getDeptId());
    }
  }

  @Override
  public int removeFavor(JwtDto jwtDto, Long menuId) {
    return menuMapper.removeFavor(jwtDto.getEmpId(), menuId);
  }

  @Override
  public List<MenuDto> getMenuById(JwtDto jwtDto, Long menuId) {
    Long empId = jwtDto.getEmpId();
    Long compId = jwtDto.getCompId();
    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getMenuForMaster(menuId, compId);
    } else {
      return menuMapper.getMenuById(menuId, empId, compId, jwtDto.getDeptId());
    }
  }

  @Override
  public List<MenuDto> getUpperMenuGnb(JwtDto jwtDto) {
    return menuMapper.getUpperMenuGnb(jwtDto.getCompId());
  }

  @Override
  public List<MenuDto> getUpperMenuLnb(JwtDto jwtDto, Long menuId) {
    return menuMapper.getUpperMenuLnb(menuId, jwtDto.getCompId());
  }
}
