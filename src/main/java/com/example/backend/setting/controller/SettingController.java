package com.example.backend.setting.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/menu/gnb")
    public ResponseEntity getGnbList() {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.getGnbList()), HttpStatus.OK);
    }

    @GetMapping("/menu")
    public ResponseEntity getMenuDetailById(@RequestParam Long menuId) {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.getMenuDetailById(menuId)), HttpStatus.OK);
    }

    @PostMapping("/menu")
    public ResponseEntity submitMenu(@RequestBody MenuRes menu, @RequestParam String type) {
        System.out.println("save menu data, type : " + type);
        System.out.println(menu.getId());
        System.out.println(menu.getParId());
        System.out.println(menu.getName());
        System.out.println(menu.getEnabledYN());
        System.out.println(menu.getSortOrder());
        System.out.println(menu.getIconUrl());
        return new ResponseEntity(settingService.submitMenu(menu, type), HttpStatus.OK);
    }

    @PostMapping("/menu/img")
    public ResponseEntity updateMenuImgById(@RequestParam("iconFile") MultipartFile iconFile) throws IOException {
        // 이미지 추가
        String path = "C:\\dz_groupware\\backend\\src\\main\\resources\\static\\image\\" + iconFile.getOriginalFilename();
        iconFile.transferTo(new File(path));
        return new ResponseEntity("iconFile saved", HttpStatus.OK);
    }

    @GetMapping("/menu/search")
    public ResponseEntity getMenuDetailByName(@RequestParam String gnbName, @RequestParam String name){
        System.out.println(gnbName + " : " + name);
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.getMenuDetailByName(gnbName, name)), HttpStatus.OK);
    }

    @GetMapping("/menu/iconList")
    public ResponseEntity getIconList() {
        return new ResponseEntity(settingService.getIconList(), HttpStatus.OK);
    }

    @GetMapping("/favor")
    public ResponseEntity checkFavor(@RequestParam Long empId, @RequestParam Long menuId) {
        System.out.println(empId +" : "+ menuId);
        // 현재 즐겨찾기 상태를 가져오기
        return new ResponseEntity(settingService.checkFavor(empId, menuId), HttpStatus.OK);
    }
    @PostMapping("/favor")
    public ResponseEntity FavorOn(Long empId, Long menuId) {
        // 즐겨찾기 저장 요청
        return new ResponseEntity(settingService.FavorOn(empId, menuId), HttpStatus.OK);
    }
    @DeleteMapping("/favor")
    public ResponseEntity deleteFavor(@RequestParam Long empId, @RequestParam Long menuId) {
        // 즐겨찾기 삭제 요청
        return new ResponseEntity(settingService.FavorOff(empId, menuId), HttpStatus.OK);
    }
}
