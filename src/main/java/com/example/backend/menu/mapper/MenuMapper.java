package com.example.backend.menu.mapper;

import com.example.backend.menu.dto.MenuRes;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

  List<MenuRes> findMenuByEmpId(Long empId);

  List<MenuRes> findFavorByEmpId(Long empId);

  int removeFavor(Long empId, Long menuId);

  List<MenuRes> findMenuByParId(Long menuId, Long compId);
}
