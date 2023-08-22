package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuRes;

import java.util.List;

public interface MenuService {
    List<MenuRes> getMenuByEmpId(Long empId);
    List<MenuRes> getFavorByEmpId(Long empId);
    int deleteFavor(Long empId, Long menuId);
}
