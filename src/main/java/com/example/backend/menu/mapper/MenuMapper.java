package com.example.backend.menu.mapper;

import com.example.backend.menu.dto.GnbDetailDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.dto.PageDto;
import com.example.backend.menu.dto.RouteDto;
import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.dto.MenuTrans;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {
  List<MenuDto> getGnbByEmpId(Long empId, Long compId, Long deptId);
  List<MenuDto> getGnbForMaster(Long compId);
  List<MenuDto> getFavorByEmpId(Long empId, Long compId, Long deptId);
  List<MenuDto> getFavorForMaster(Long empId);
  int removeFavor(Long empId, Long menuId);
  List<MenuDto> getMenuById(Long menuId, Long empId, Long compId, Long deptId);
  List<MenuDto> getMenuForMaster(Long menuId, Long compId);
  List<RouteDto> getMenuList(Long empId, Long deptId, Long compId);
  List<RouteDto> getMenuListForMaster(Long compId);

  List<GnbDetailDto> getGnbList(Long compId);
  List<MenuRes> findLnb(String gnbName, String name);


  void addMenu(MenuRes menu);
  void modifyParId(Long id, String idTree);
  void updateChildNodeYn(Long parId);
  void modifyIdTree(Long id, String idTree);
  List<MenuTrans> getMoveMenuList(String id);
  void modifyPreMoveMenu(MenuTrans menuTrans);
  //  void modifyMoveMenuList(List<MenuTrans> menuList);
  MenuTrans getMenuByMenuId(Long parId);
  int checkMenuInMenu(String id, Long parId);
  List<MenuDto> getUpperMenuGnb(Long compId);
  List<MenuDto> getUpperMenuLnb(Long menuId, Long compId);
  List<MenuRes> findMenuByName(String gnbName, String name, Long compId);
  List<MenuRes> findMenuByOption(String gnbName, String name, Long pageId, Long compId);
  List<MenuTrans> findChildAll(String id);
  void modifyChildNameTree(MenuTrans menuTrans);
  void modifyMenuById(MenuRes menu);
  List<Long> findChildAllMenu(String StringId, Long id);
  void deleteMenu(Long menuId);
  void modifyMenuParId(Long parId, Long id, String idTree);
  List<PageDto> getPageList();

  Long getTmpPk();
  void insertDefaultMenu(Long parId, String idTree, Long compId);

  void updateDefaultMenu(Long id, Long parId, String idTree);
//  void modifyMenuParId(Long parId, Long id, String idTree);

//  MenuTrans getParIdOfUpperMenu(Long id);
//  void modifyPreMoveMenu(MenuTrans menu);
//  MenuTrans getParMenu(Long id);
//  List<MenuDto> getUpperMenuLnbForMaster(Long menuId, Long compId);
}