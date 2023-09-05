package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.mapper.MenuMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;

  public MenuServiceImpl(MenuMapper menuMapper) {
    this.menuMapper = menuMapper;
  }

  @Override
  public List<MenuRes> findMenuByEmpId(Long empId) {
    return menuMapper.findMenuByEmpId(empId);
  }

  @Override
  public List<MenuRes> findFavorByEmpId(Long empId) {
    return menuMapper.findFavorByEmpId(empId);
  }

  @Override
  public int removeFavor(Long empId, Long menuId) {
    return menuMapper.removeFavor(empId, menuId);
  }

  @Override
  public List<MenuRes> findMenuByParId(Long menuId, Long compId) {
    return menuMapper.findMenuByParId(menuId, compId);
  }
}
