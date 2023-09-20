package com.example.backend.setting.service;

import com.example.backend.setting.dto.Dto;
import com.example.backend.setting.dto.JwtDto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;

public interface SettingService {
  void testRedisAndJwt();

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  int saveMenu(MenuRes menu, String type);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  String findFavorById(Long menuId) throws JsonProcessingException;

  int modifyFavorOn(Long menuId) throws JsonProcessingException;

  int modifyFavorOff(Long menuId) throws JsonProcessingException;

  List<MenuRes> findAllMenu(Long compId);
  int deleteMenu(Long menuId);

  MenuTrans modifyMenu(MenuTrans menu);
  List<Dto> testList();

  void testRedisModify();

  Map<String, Object> testGetInfo();

  JwtDto testJwtPayload() throws JsonProcessingException;
}
