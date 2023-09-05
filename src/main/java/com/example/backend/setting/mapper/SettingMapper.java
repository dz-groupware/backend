package com.example.backend.setting.mapper;

import com.example.backend.setting.dto.MenuRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SettingMapper {
    List<MenuRes> findGnbList();
    List<MenuRes> findMenuDetailById(Long menuId);
    int addMenu(MenuRes menu);
    int modifyParId();
    int modifyMenuById(MenuRes menu);
    List<MenuRes> findMenuByName(String gnbName, String name);

    List<String> findAllIcon ();
    int findFavorById (Long empId, Long menuId);
    int modifyFavorOn (Long empId, Long menuId);
    int modifyFavorOff (Long empId, Long menuId);
    List<MenuRes> findAllMenu (Long compId);
}

