package com.example.backend.setting.service;

import com.example.backend.config.jwt.PkDto;
import com.example.backend.setting.dto.Dto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import java.util.List;

public interface SettingService {
//  void testRedisAndJwt();

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  int saveMenu(PkDto pkDto, MenuRes menu, String type);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  String findFavorById(PkDto pkDto, Long menuId);

  int modifyFavorOn(PkDto pkDto, Long menuId);

  int modifyFavorOff(PkDto pkDto, Long menuId);

  List<MenuRes> findAllMenu(Long compId);
  int deleteMenu(Long menuId);

  MenuTrans modifyMenu(MenuTrans menu);
  List<Dto> testList();

  void testRedisModify();

//  Map<String, Object> testGetInfo();

//  JwtDto testJwtPayload() throws JsonProcessingException;
}
