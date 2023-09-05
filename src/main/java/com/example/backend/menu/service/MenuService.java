package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuRes;
import java.util.List;

public interface MenuService {

  List<MenuRes> findMenuByEmpId(Long empId);

  List<MenuRes> findFavorByEmpId(Long empId);

  int removeFavor(Long empId, Long menuId);

  List<MenuRes> findMenuByParId(Long menuId, Long compId);
}
