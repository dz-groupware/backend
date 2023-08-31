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
    public ResponseEntity findGnbList() {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.findGnbList()), HttpStatus.OK);
    }

    @GetMapping("/menu")
    public ResponseEntity findMenuDetailById (@RequestParam Long menuId) {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.findMenuDetailById(menuId)), HttpStatus.OK);
    }

    @PostMapping("/menu")
    public ResponseEntity saveMenu(@RequestBody MenuRes menu, @RequestParam String type) {
        return new ResponseEntity(settingService.saveMenu(menu, type), HttpStatus.OK);
    }

    @PostMapping("/menu/img")
    public ResponseEntity updateMenuImgById(@RequestParam("iconFile") MultipartFile iconFile) throws IOException {
        // 이미지 추가
        String path = "C:\\dz_groupware\\backend\\src\\main\\resources\\image\\" + iconFile.getOriginalFilename();
        iconFile.transferTo(new File(path));
        return new ResponseEntity("iconFile saved", HttpStatus.OK);
    }

    @GetMapping("/menu/search")
    public ResponseEntity findMenuByName(@RequestParam String gnbName, @RequestParam String name){
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(settingService.findMenuByName(gnbName, name)), HttpStatus.OK);
    }

    @GetMapping("/menu/iconList")
    public ResponseEntity findAllIcon() {
        return new ResponseEntity(settingService.findAllIcon(), HttpStatus.OK);
    }

    @GetMapping("/favor")
    public ResponseEntity findFavorById(@RequestParam Long empId, @RequestParam Long menuId) {
        // 현재 즐겨찾기 상태를 가져오기
        return new ResponseEntity(settingService.findFavorById(empId, menuId), HttpStatus.OK);
    }

    @PostMapping("/favor")
    public ResponseEntity modifyFavorOn(Long empId, Long menuId) {
        // 즐겨찾기 저장 요청
        return new ResponseEntity(settingService.modifyFavorOn(empId, menuId), HttpStatus.OK);
    }

    @DeleteMapping("/favor")
    public ResponseEntity modifyFavorOff(@RequestParam Long empId, @RequestParam Long menuId) {
        // 즐겨찾기 삭제 요청
        return new ResponseEntity(settingService.modifyFavorOff(empId, menuId), HttpStatus.OK);
    }

}
