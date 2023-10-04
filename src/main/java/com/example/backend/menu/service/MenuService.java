package com.example.backend.menu.service;

import com.example.backend.menu.dto.GnbDetailDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.config.jwt.PkDto;
import com.example.backend.menu.dto.PageDto;
import com.example.backend.menu.dto.RouteDto;
import com.example.backend.menu.dto.MenuRes;
import java.util.List;

public interface MenuService {
  List<MenuDto> getGnbById(PkDto pkDto) ;
  List<MenuDto> getFavorByEmpId(PkDto pkDto) ;
  int removeFavor(PkDto pkDto, Long menuId) ;
  List<MenuDto> getMenuById(PkDto pkDto, Long menuId) ;
  List<MenuDto> getUpperMenuGnb(PkDto pkDto) ;
  List<MenuDto> getUpperMenuLnb(PkDto pkDto, Long menuId);
  List<RouteDto> getMenuList(PkDto pkDto);
  List<MenuRes> findLnb(String gnbName, String name);
  int saveMenu(PkDto pkDto, MenuRes menu, String type);
  List<GnbDetailDto> getGnbList(PkDto pkDto);
  int deleteMenu(Long menuId);
  int deleteMenuLnb(Long menuId);
  List<PageDto> getPageList();
//  MenuTrans modifyMenu(MenuTrans menu);
}
