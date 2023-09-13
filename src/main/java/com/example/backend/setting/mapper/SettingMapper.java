package com.example.backend.setting.mapper;

import com.example.backend.setting.dto.Menu;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingMapper {

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  int addMenu(MenuRes menu);

  int modifyParId(Long id, String idTree);
  int modifyMenuParId(Long parId, Long id, String idTree);
  int modifyGnbById();

  int modifyMenuById(MenuRes menu);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  int findFavorById(Long empId, Long menuId);

  int modifyFavorOn(Long empId, Long menuId);

  int modifyFavorOff(Long empId, Long menuId);

  List<MenuRes> findAllMenu(Long compId);

  MenuRes getUpperMenuById(Long parId);
  int modifyUpperMenu(Long parId);
  int deleteMenu(Long menuId);
  List<Long> getMenuIdByIdTree(String menuId);

  int checkMenuInMenu(String id, Long parId);

  MenuTrans getParIdOfUpperMenu(Long id);
  List<MenuTrans> getPreMoveMenuList(String id);

  void modifyPreMoveMenu(MenuTrans menu);
  MenuTrans getParMenu(Long id);
  MenuTrans getPreParMenu(Long id);

}

