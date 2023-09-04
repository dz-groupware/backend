package com.example.backend.menu.mapper;

import com.example.backend.menu.dto.MenuRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuRes> findMenuByEmpId(Long empId);
    List<MenuRes> findFavorByEmpId(Long empId);
    int removeFavor(Long empId, Long menuId);
    List<MenuRes> findMenuByParId (Long menuId, Long compId);
}
