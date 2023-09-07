package com.example.backend.menu.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;

  public MenuServiceImpl(MenuMapper menuMapper) {
    this.menuMapper = menuMapper;
  }

  @Override
  public List<MenuDto> getMenuByEmpId() {
    // 마스터인 경우 :  dept가 null일 수 있음.

    Long empId = SecurityUtil.getEmployeeId();
    Long compId = SecurityUtil.getCompanyId();
    Long deptId = SecurityUtil.getDepartmentId();

    return menuMapper.getMenuByEmpId(empId, compId, deptId);

  }


  @Override
  public List<MenuDto> getFavorByEmpId(Long empId) {
    return menuMapper.getFavorByEmpId(empId);
  }

  @Override
  public int removeFavor(Long empId, Long menuId) {
    return menuMapper.removeFavor(empId, menuId);
  }

  @Override
  public List<MenuDto> findMenuByParId(Long menuId, Long compId) {
    return menuMapper.findMenuByParId(menuId, compId);
  }

}
