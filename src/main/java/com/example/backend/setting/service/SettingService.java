package com.example.backend.setting.service;

import com.example.backend.setting.dto.Dto;
import com.example.backend.setting.dto.JwtDto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;

public interface SettingService {
//  void testRedisAndJwt();

  List<MenuRes> findGnbList();

  List<MenuRes> findMenuDetailById(Long menuId);

  int saveMenu(JwtDto jwtDto, MenuRes menu, String type);

  List<MenuRes> findMenuByName(String gnbName, String name);

  List<String> findAllIcon();

  String findFavorById(JwtDto jwtDto, Long menuId);

  int modifyFavorOn(JwtDto jwtDto, Long menuId);

  int modifyFavorOff(JwtDto jwtDto, Long menuId);

  List<MenuRes> findAllMenu(Long compId);
  int deleteMenu(Long menuId);

  MenuTrans modifyMenu(MenuTrans menu);
  List<Dto> testList();

  void testRedisModify();

//  Map<String, Object> testGetInfo();

//  JwtDto testJwtPayload() throws JsonProcessingException;
}
