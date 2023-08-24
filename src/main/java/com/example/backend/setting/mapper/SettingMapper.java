package com.example.backend.setting.mapper;

import com.example.backend.setting.dto.MenuRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SettingMapper {
    List<MenuRes> getMenuDetailById(Long menuId);
    int insertMenuById(MenuRes menu);
    int updateMenuImgById(int menuId, byte[] iconFile);



}

