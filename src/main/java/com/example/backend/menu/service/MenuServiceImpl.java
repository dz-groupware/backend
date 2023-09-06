package com.example.backend.menu.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;

  public MenuServiceImpl(MenuMapper menuMapper) {
    this.menuMapper = menuMapper;
  }

  @Override
  public List<MenuDto> getMenuByEmpId(Long userId, Long empId, Long deptId, Long compId) {
    return menuMapper.getMenuByEmpId(empId);
  }

  @Override
  public List<MenuDto> getFavorByEmpId(Long empId) {
    return menuMapper.findFavorByEmpId(empId);
  }

  @Override
  public int removeFavor(Long empId, Long menuId) {
    return menuMapper.removeFavor(empId, menuId);
  }

  @Override
  public List<MenuDto> findMenuByParId(Long menuId, Long compId) {
    return menuMapper.findMenuByParId(menuId, compId);
  }

  private List<Long> check(Long userId, Long empId, Long deptId, Long compId){
    return menuMapper.check(userId, empId, deptId, compId);
  }
}
