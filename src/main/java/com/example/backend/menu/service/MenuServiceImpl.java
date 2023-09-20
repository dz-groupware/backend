package com.example.backend.menu.service;

import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import com.example.backend.redis.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;
  private final CheckMapper checkMapper;
  private final RedisService redisService;

  public MenuServiceImpl(MenuMapper menuMapper, CheckMapper checkMapper, RedisService redisService) {
    this.menuMapper = menuMapper;
    this.checkMapper = checkMapper;
    this.redisService = redisService;
  }

  @Override
  public List<MenuDto> getGnbById() throws JsonProcessingException {

    Long empId = redisService.getInfo().getEmpId();
    Long compId = redisService.getInfo().getCompId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getGnbForMaster(redisService.getInfo().getCompId());
    } else {
      return menuMapper.getGnbByEmpId(empId, compId, redisService.getInfo().getDeptId());
    }
  }

  @Override
  public List<MenuDto> getFavorByEmpId() throws JsonProcessingException {

    Long empId = redisService.getInfo().getEmpId();
    Long compId = redisService.getInfo().getCompId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getFavorForMaster(compId);
    } else {
      return menuMapper.getFavorByEmpId(empId, compId, redisService.getInfo().getDeptId());
    }
  }

  @Override
  public int removeFavor(Long menuId) throws JsonProcessingException {
    return menuMapper.removeFavor(redisService.getInfo().getEmpId(), menuId);
  }

  @Override
  public List<MenuDto> getMenuById(Long menuId) throws JsonProcessingException {

    Long empId = redisService.getInfo().getEmpId();
    Long compId = redisService.getInfo().getCompId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getMenuForMaster(menuId, compId);
    } else {
      return menuMapper.getMenuById(menuId, empId, compId, redisService.getInfo().getDeptId());
    }
  }

  @Override
  public List<MenuDto> getUpperMenuGnb() throws JsonProcessingException {
    return menuMapper.getUpperMenuGnb(redisService.getInfo().getCompId());
  }

  @Override
  public List<MenuDto> getUpperMenuLnb(Long menuId) throws JsonProcessingException {
    return menuMapper.getUpperMenuLnb(menuId, redisService.getInfo().getCompId());
  }
}
