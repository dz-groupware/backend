package com.example.backend.setting.service;

import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.mapper.SettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SettingServiceImpl  implements SettingService {
    private final SettingMapper settingMapper;

    public SettingServiceImpl(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
    }
    public List<MenuRes> getMenuDetailById(Long menuId){
        return settingMapper.getMenuDetailById(menuId);
    }

    public int updateMenuById(MenuRes menu){
        return settingMapper.insertMenuById(menu);
    }
    public int updateMenuImgById(String id, MultipartFile file) throws IOException {
        int menuId = Integer.valueOf(id);
        byte[] iconFile = file.getBytes();
        return settingMapper.updateMenuImgById(menuId, iconFile);
    }




}
