package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface MenuService {
  List<MenuDto> getGnbById() throws JsonProcessingException;
  List<MenuDto> getFavorByEmpId() throws JsonProcessingException;
  int removeFavor(Long menuId) throws JsonProcessingException;
  List<MenuDto> getMenuById(Long menuId) throws JsonProcessingException;
  List<MenuDto> getUpperMenuGnb() throws JsonProcessingException;
  List<MenuDto> getUpperMenuLnb(Long menuId) throws JsonProcessingException;
}
