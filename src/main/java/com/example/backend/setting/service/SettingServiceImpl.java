package com.example.backend.setting.service;

import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.mapper.SettingMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Service
public class SettingServiceImpl  implements SettingService {
    private final SettingMapper settingMapper;

    public SettingServiceImpl(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
    }

    public List<MenuRes> getGnbList() {
        return settingMapper.getGnbList();
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

    public List<MenuRes> getMenuDetailByName(String gnbName, String name){
        return settingMapper.getMenuDetailByName(gnbName, name);
    }

    public List<String> getIconList() {
        return settingMapper.getIconList();
    }

    // 즐겨찾기
    public String checkFavor (Long empId, Long menuId){
        int check = settingMapper.checkFavor(empId, menuId);
        if (check == 0) {
            return "false";
        }
        if (check == 1) {
            return "true";
        }
        // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
        FavorOff(empId, menuId);
        return "false";
    }
    public int FavorOn (Long empId, Long menuId) {
        return settingMapper.FavorOn(empId, menuId);
    }
    public int FavorOff (Long empId, Long menuId) {
        return settingMapper.FavorOff(empId, menuId);
    }


}
