package com.example.backend.setting.service;

import com.example.backend.setting.dto.MenuRes;
import java.util.List;

public interface SettingService {

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  int saveMenu(MenuRes menu, String type);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  String findFavorById(Long empId, Long menuId);

  int modifyFavorOn(Long empId, Long menuId);

  int modifyFavorOff(Long empId, Long menuId);

  List<MenuRes> findAllMenu(Long compId);
}
