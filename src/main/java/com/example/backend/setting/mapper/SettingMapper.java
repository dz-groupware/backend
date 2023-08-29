package com.example.backend.setting.mapper;

import com.example.backend.setting.dto.MenuRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SettingMapper {
    List<MenuRes> getGnbList();
    List<MenuRes> getMenuDetailById(Long menuId);
    int insertMenuById(MenuRes menu);
    int updateMenuImgById(int menuId, byte[] iconFile);
    List<MenuRes> getMenuDetailByName(String gnbName, String name);

    List<String> getIconList ();
    int checkFavor (Long empId, Long menuId);
    int FavorOn (Long empId, Long menuId);
    int FavorOff (Long empId, Long menuId);

}

