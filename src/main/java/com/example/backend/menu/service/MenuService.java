package com.example.backend.menu.service;

import com.example.backend.menu.dto.GnbDetailDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.dto.PageDto;
import com.example.backend.menu.dto.RouteDto;
import com.example.backend.menu.dto.MenuRes;
import java.util.List;

public interface MenuService {
  List<MenuDto> getGnbById() ;
  List<MenuDto> getFavorByEmpId() ;
  int removeFavor(Long menuId) ;
  List<MenuDto> getMenuById(Long menuId) ;
  List<MenuDto> getUpperMenuGnb() ;
  List<MenuDto> getUpperMenuLnb( Long menuId);
  List<RouteDto> getMenuList();
  List<MenuRes> findLnb(String gnbName, String name, Long pageId);
  int saveMenu(MenuRes menu, String type);
  List<GnbDetailDto> getGnbList();
  int deleteMenu(Long menuId);
  int deleteMenuLnb(Long menuId);
  List<PageDto> getPageList();
  void insertDefaultMenu(Long compId);
//  MenuTrans modifyMenu(MenuTrans menu);
}
