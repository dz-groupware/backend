package com.example.backend.setting.service;

import com.example.backend.setting.dto.MenuRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SettingService {
    List<MenuRes> getGnbList();
    List<MenuRes> getMenuDetailById(Long menuId);
    int submitMenu(MenuRes menu, String type);

    int updateMenuImgById(String menuId, MultipartFile iconFile) throws IOException;
    List<MenuRes> getMenuDetailByName(String gnbName, String name);
    List<String> getIconList ();
    String checkFavor (Long empId, Long menuId);
    int FavorOn (Long empId, Long menuId);
    int FavorOff (Long empId, Long menuId);
}
