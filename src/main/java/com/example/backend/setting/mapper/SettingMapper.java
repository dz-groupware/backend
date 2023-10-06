package com.example.backend.setting.mapper;

import com.example.backend.setting.dto.Dto;
import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.dto.MenuTrans;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingMapper {
  List<MenuRes> findGnbList();
  List<MenuRes> findMenuDetailById(Long menuId);







  int modifyGnbById();
  List<String> findAllIcon();
  List<MenuRes> findAllMenu(Long compId);

  List<Long> getMenuIdByIdTree(String menuId);

  MenuTrans getPreParMenu(Long id);
  int updateChildNodeYnOfParMenu(Long id);
//  void testList(DtoList list);
  void testList(List<Dto> list);

}

