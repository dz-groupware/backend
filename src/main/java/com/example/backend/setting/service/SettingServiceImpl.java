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
    public List<MenuRes> findGnbList() {
        return settingMapper.findGnbList();
    }
    @Override
    public List<MenuRes> findMenuDetailById(Long menuId){
        return settingMapper.findMenuDetailById(menuId);
    }
    @Override
    public int saveMenu(MenuRes menu, String type){
        if (type.equals("1")){
            try {
                settingMapper.addMenu(menu);
                return settingMapper.modifyParId();
            } catch (Exception e) {
                return 10;
            }
        }
        if (type.equals("2")){
            return settingMapper.modifyMenuById(menu);
        }
        if (type.equals("3")){
            return settingMapper.addMenu(menu);
        }
        if (type.equals("4")){
            return settingMapper.modifyMenuById(menu);
        }
        return 10;
    }

    @Override
    public List<MenuRes> findMenuByName(String gnbName, String name){
        return settingMapper.findMenuByName(gnbName, name);
    }
    @Override
    public List<String> findAllIcon() {
        return settingMapper.findAllIcon();
    }

    // 즐겨찾기
    @Override
    public String findFavorById (Long empId, Long menuId){
        int check = settingMapper.findFavorById(empId, menuId);
        if (check == 0) {
            return "false";
        }
        if (check == 1) {
            return "true";
        }
        // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
        modifyFavorOff(empId, menuId);
        return "false";
    }
    @Override
    public int modifyFavorOn (Long empId, Long menuId) {
        return settingMapper.modifyFavorOn(empId, menuId);
    }
    @Override
    public int modifyFavorOff (Long empId, Long menuId) {
        return settingMapper.modifyFavorOff(empId, menuId);
    }


}
