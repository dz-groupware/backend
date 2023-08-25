package com.example.backend.setting.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.time.LocalTime.now;

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
        menu.setIconUrl(menu.getIconUrl()+now());

        return new ResponseEntity(settingService.updateMenuById(menu), HttpStatus.OK);
    }

    @PostMapping("/menu/img")
    public ResponseEntity updateMenuImgById(@RequestParam("iconFile") MultipartFile iconFile) throws IOException {

        String path = "C:\\dz_groupware\\backend\\src\\main\\resources\\image\\" + iconFile.getOriginalFilename();

        iconFile.transferTo(new File(path));
        return new ResponseEntity("iconFile saved", HttpStatus.OK);
    }
}
