package com.example.backend.menu.service;

import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import java.util.ArrayList;
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
  public List<MenuDto> getGnbById() {
    Long empId = SecurityUtil.getEmployeeId();
    Long compId = SecurityUtil.getCompanyId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getGnbForMaster(compId);
    }

    Long userId = SecurityUtil.getUserId();
    Long deptId = SecurityUtil.getDepartmentId();
    List<Long> result = checkMapper.checkExits(userId, empId, deptId, compId);
    System.out.println("[check] UID : "+userId+" EID : "+empId+" DID : "+deptId+" CID : "+compId);
    System.out.println("result size : "+result.size()+" and get(0) : "+result.get(0));

    if( result.size() == 1 && result.get(0) == 1L ){
      return menuMapper.getGnbByEmpId(empId, compId, deptId);
    }

    return new ArrayList<>();
  }

  @Override
  public List<MenuDto> getFavorByEmpId() {

    Long empId = SecurityUtil.getEmployeeId();
    Long compId = SecurityUtil.getCompanyId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getFavorForMaster(compId);
    }

    Long userId = SecurityUtil.getUserId();
    Long deptId = SecurityUtil.getDepartmentId();
    List<Long> result = checkMapper.checkExits(userId, empId, deptId, compId);

    if( result.size() == 1 && result.get(0) == 1L ){
      return menuMapper.getFavorByEmpId(empId, compId, deptId);
    }

    return new ArrayList<>();
  }

  @Override
  public int removeFavor(Long menuId) {
    Long empId = SecurityUtil.getEmployeeId();
    return menuMapper.removeFavor(empId, menuId);
  }

  @Override
  public List<MenuDto> getMenuById(Long menuId) {

    Long empId = SecurityUtil.getEmployeeId();
    Long compId = SecurityUtil.getCompanyId();

    if(checkMapper.checkMaster(empId)) {
      // 마스터인 경우
      return menuMapper.getMenuForMaster(menuId, compId);
    }

    Long userId = SecurityUtil.getUserId();
    Long deptId = SecurityUtil.getDepartmentId();
    List<Long> result = checkMapper.checkExits(userId, empId, deptId, compId);

    if( result.size() == 1 && result.get(0) == 1L ){
      return menuMapper.getMenuById(menuId, empId, compId, deptId);
    }

    return new ArrayList<>();
  }

}
