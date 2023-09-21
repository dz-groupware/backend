package com.example.backend.menu.service;

import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;
  private final CheckMapper checkMapper;
  private static ThreadLocal<JwtDto> jwtThreadLocal = new ThreadLocal<>();

  public MenuServiceImpl(MenuMapper menuMapper, CheckMapper checkMapper) {
    this.menuMapper = menuMapper;
    this.checkMapper = checkMapper;
  }

  @Override
  public List<MenuDto> getGnbById() {
    JwtDto jwtDto = jwtThreadLocal.get();

    if(checkMapper.checkMaster(jwtDto.getEmpId())) {
      // 마스터인 경우
      return menuMapper.getGnbForMaster(jwtDto.getCompId());
    } else {
      return menuMapper.getGnbByEmpId(jwtDto.getEmpId(), jwtDto.getCompId(), jwtDto.getDeptId());
    }
  }

  @Override
  public List<MenuDto> getFavorByEmpId() throws JsonProcessingException {

    Long empId = jwtThreadLocal.get().getEmpId();
    Long compId = jwtThreadLocal.get().getCompId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getFavorForMaster(compId);
    } else {
      return menuMapper.getFavorByEmpId(empId, compId, jwtThreadLocal.get().getDeptId());
    }
  }

  @Override
  public int removeFavor(Long menuId) throws JsonProcessingException {
    return menuMapper.removeFavor(jwtThreadLocal.get().getEmpId(), menuId);
  }

  @Override
  public List<MenuDto> getMenuById(Long menuId) throws JsonProcessingException {

    Long empId = jwtThreadLocal.get().getEmpId();
    Long compId = jwtThreadLocal.get().getCompId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getMenuForMaster(menuId, compId);
    } else {
      return menuMapper.getMenuById(menuId, empId, compId, jwtThreadLocal.get().getDeptId());
    }
  }

  @Override
  public List<MenuDto> getUpperMenuGnb() throws JsonProcessingException {
    return menuMapper.getUpperMenuGnb(jwtThreadLocal.get().getCompId());
  }

  @Override
  public List<MenuDto> getUpperMenuLnb(Long menuId) throws JsonProcessingException {
    return menuMapper.getUpperMenuLnb(menuId, jwtThreadLocal.get().getCompId());
  }
}
