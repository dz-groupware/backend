package com.example.backend.menu.mapper;

import com.example.backend.menu.dto.MenuRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuRes> getMenuByEmpId(Long empId);
    List<MenuRes> getFavorByEmpId(Long empId);
    int deleteFavor(Long empId, Long menuId);
}
