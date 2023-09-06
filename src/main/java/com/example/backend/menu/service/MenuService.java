package com.example.backend.menu.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import java.util.List;

public interface MenuService {

  List<MenuDto> getMenuByEmpId(Long userId, Long empId, Long deptId, Long compId);

  List<MenuDto> getFavorByEmpId(Long empId);

  int removeFavor(Long empId, Long menuId);

  List<MenuDto> findMenuByParId(Long menuId, Long compId);
}
