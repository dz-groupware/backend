package com.example.backend.setting.mapper;

import com.example.backend.setting.dto.Dto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingMapper {

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  void addMenu(MenuRes menu);

  void modifyParId(Long id, String idTree);
  void modifyMenuParId(Long parId, Long id, String idTree);
  int modifyGnbById();

  void modifyMenuById(MenuRes menu);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  int findFavorById(Long empId, Long menuId);

  int modifyFavorOn(Long empId, Long menuId);

  int modifyFavorOff(Long empId, Long menuId);

  List<MenuRes> findAllMenu(Long compId);

  MenuRes getUpperMenuById(Long parId);
  void modifyUpperMenu(Long parId);
  void deleteMenu(Long menuId);
  List<Long> getMenuIdByIdTree(String menuId);

  int checkMenuInMenu(String id, Long parId);

  MenuTrans getParIdOfUpperMenu(Long id);
  List<MenuTrans> getPreMoveMenuList(String id);

  void modifyPreMoveMenu(MenuTrans menu);
  MenuTrans getParMenu(Long id);
  MenuTrans getPreParMenu(Long id);
  int updateChildNodeYnOfParMenu(Long id);

//  void testList(DtoList list);


  void testList(List<Dto> list);

}

