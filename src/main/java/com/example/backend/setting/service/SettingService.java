package com.example.backend.setting.service;

import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import java.util.List;

public interface SettingService {
  void testRedisAndJwt();

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  int saveMenu(MenuRes menu, String type);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  String findFavorById(Long menuId);

  int modifyFavorOn(Long menuId);

  int modifyFavorOff(Long menuId);

  List<MenuRes> findAllMenu(Long compId);
  int deleteMenu(Long menuId);

  MenuTrans modifyMenu(MenuTrans menu);
}
