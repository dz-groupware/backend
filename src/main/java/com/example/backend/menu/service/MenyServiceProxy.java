package com.example.backend.menu.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceProxy implements MenuService{
  private final MenuMapper menuMapper;
  private final MenuService menuService;

  public MenuServiceProxy(MenuMapper menuMapper, MenuService menuService) {
    this.menuMapper = menuMapper;
    this.menuService = menuService;
  }

  @Override
  public List<MenuDto> getMenuByEmpId(Long userId, Long empId, Long deptId, Long compId) {
    List<Long> check = menuMapper.check(userId, empId, deptId, compId);

    if (check.size() == 1) {
      if (check.get(0) == 1L) {
        return menuService.getMenuByEmpId(userId, empId, deptId, compId);
      }
      return new ArrayList<MenuDto>(new MenuDto("-1"));
    }
    return new SingleResponseDto<Map<String, List<Long>>>(check);
  }

  @Override
  public List<MenuDto> getFavorByEmpId(Long empId) {
    return null;
  }

  @Override
  public int removeFavor(Long empId, Long menuId) {
    return 0;
  }

  @Override
  public List<MenuDto> findMenuByParId(Long menuId, Long compId) {
    return null;
  }
}
