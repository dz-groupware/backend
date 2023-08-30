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

    @Override
    public List<MenuRes> getGnbList() {
        return settingMapper.getGnbList();
    }
    @Override
    public List<MenuRes> getMenuDetailById(Long menuId){
        return settingMapper.getMenuDetailById(menuId);
    }
    @Override
    public int submitMenu(MenuRes menu, String type){
        if (type.equals("1")){
            System.out.println("type is "+type);
            try {
                settingMapper.insertMenuById(menu);
                return settingMapper.updateMenuParId();
            } catch (Exception e) {
                return 10;
            }
        }
        if (type.equals("2")){
            System.out.println("type is "+type);
            return settingMapper.updateMenuById(menu);
        }
        if (type.equals("3")){
            System.out.println("type is "+type);
            return settingMapper.insertMenuById(menu);
        }
        if (type.equals("4")){
            System.out.println("type is "+type);
            return settingMapper.updateMenuById(menu);
        }
        System.out.println("fail : type is "+type);
        return 10;
    }

    @Override
    public int updateMenuImgById(String id, MultipartFile file) throws IOException {
        int menuId = Integer.valueOf(id);
        byte[] iconFile = file.getBytes();
        return settingMapper.updateMenuImgById(menuId, iconFile);
    }
    @Override
    public List<MenuRes> getMenuDetailByName(String gnbName, String name){
        return settingMapper.getMenuDetailByName(gnbName, name);
    }
    @Override
    public List<String> getIconList() {
        return settingMapper.getIconList();
    }

    // 즐겨찾기
    @Override
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
    @Override
    public int FavorOn (Long empId, Long menuId) {
        return settingMapper.FavorOn(empId, menuId);
    }
    @Override
    public int FavorOff (Long empId, Long menuId) {
        return settingMapper.FavorOff(empId, menuId);
    }


}
