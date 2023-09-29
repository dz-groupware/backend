package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuDto;
import com.example.backend.config.jwt.PkDto;
import java.util.List;

public interface MenuService {
  List<MenuDto> getGnbById(PkDto pkDto) ;
  List<MenuDto> getFavorByEmpId(PkDto pkDto) ;
  int removeFavor(PkDto pkDto, Long menuId) ;
  List<MenuDto> getMenuById(PkDto pkDto, Long menuId) ;
  List<MenuDto> getUpperMenuGnb(PkDto pkDto) ;
  List<MenuDto> getUpperMenuLnb(PkDto pkDto, Long menuId) ;
}
