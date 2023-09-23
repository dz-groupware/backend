package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuDto;
import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface MenuService {
  List<MenuDto> getGnbById(JwtDto jwtDto) ;
  List<MenuDto> getFavorByEmpId(JwtDto jwtDto) ;
  int removeFavor(JwtDto jwtDto, Long menuId) ;
  List<MenuDto> getMenuById(JwtDto jwtDto, Long menuId) ;
  List<MenuDto> getUpperMenuGnb(JwtDto jwtDto) ;
  List<MenuDto> getUpperMenuLnb(JwtDto jwtDto, Long menuId) ;
}
