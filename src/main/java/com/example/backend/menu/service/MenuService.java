package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuDto;
import java.util.List;

public interface MenuService {
  List<MenuDto> getGnbById();
  List<MenuDto> getFavorByEmpId();
  int removeFavor(Long menuId);
  List<MenuDto> getMenuById(Long menuId);
  List<MenuDto> getUpperMenuGnb();
  List<MenuDto> getUpperMenuLnb(Long menuId);
}
