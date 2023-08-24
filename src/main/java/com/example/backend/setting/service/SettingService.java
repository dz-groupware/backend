package com.example.backend.setting.service;

import com.example.backend.setting.dto.MenuRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SettingService {
    List<MenuRes> getMenuDetailById(Long menuId);
    int updateMenuById(MenuRes menu);

    int updateMenuImgById(String menuId, MultipartFile iconFile) throws IOException;

}
