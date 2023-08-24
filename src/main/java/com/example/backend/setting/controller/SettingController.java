package com.example.backend.setting.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/setting")
public class SettingController {
    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/menu")
    public ResponseEntity getMenuDetailById(@RequestParam Long menuId) {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.getMenuDetailById(menuId)), HttpStatus.OK);
    }

    @PostMapping("/menu")
    public ResponseEntity updateMenuById(@RequestBody MenuRes menu) {
        System.out.println(menu.getId());
        System.out.println(menu.getName());
        System.out.println(menu.getEnabledYN());
        System.out.println(menu.getSortOrder());
        System.out.println(menu.getIconFile());
        System.out.println(menu.getParId());
        System.out.println(menu.getIconUrl());
        System.out.println(menu.getTreePath());
        System.out.println(menu.getChildNodeYN());
        return new ResponseEntity(settingService.updateMenuById(menu), HttpStatus.OK);
    }

    @PostMapping("/menu/img")
    public ResponseEntity updateMenuImgById(@RequestParam("id") String id, @RequestParam("iconFile") MultipartFile iconFile) throws IOException {
        System.out.println(id);
        System.out.println(iconFile);
        System.out.println(iconFile.getBytes());
        return new ResponseEntity(settingService.updateMenuImgById(id, iconFile), HttpStatus.OK);
    }
}
