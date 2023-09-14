package com.example.backend.setting.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.service.SettingService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/setting")
public class SettingController {

  private final SettingService settingService;

  public SettingController(SettingService settingService) {
    this.settingService = settingService;
  }

  @GetMapping("/menu/gnb")
  public ResponseEntity<?> findGnbList() {
    return new ResponseEntity<>(new SingleResponseDto<List<MenuRes>>(settingService.findGnbList()),
        HttpStatus.OK);
  }

  @GetMapping("/menu")
  public ResponseEntity<?> findMenuDetailById(@RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuRes>>(settingService.findMenuDetailById(menuId)),
        HttpStatus.OK);
  }

  @PostMapping("/menu")
  public ResponseEntity<?> saveMenu(@RequestBody MenuRes menu, @RequestParam String type) {
    return new ResponseEntity<>(settingService.saveMenu(menu, type), HttpStatus.OK);
  }

  @DeleteMapping("/menu")
  public ResponseEntity<?> deleteMenu(@RequestParam Long menuId) {
    return new ResponseEntity<>(settingService.deleteMenu(menuId), HttpStatus.OK);
  }

  @GetMapping("/menu/search")
  public ResponseEntity<?> findMenuByName(@RequestParam String gnbName, @RequestParam String name) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuRes>>(settingService.findMenuByName(gnbName, name)),
        HttpStatus.OK);
  }

  @GetMapping("/favor")
  public ResponseEntity<?> findFavorById(@RequestParam Long empId, @RequestParam Long menuId) {
    // 현재 즐겨찾기 상태를 가져오기
    return new ResponseEntity<>(new SingleResponseDto<>(settingService.findFavorById(empId, menuId)), HttpStatus.OK);
  }

  @PostMapping("/favor")
  public ResponseEntity<?> modifyFavorOn(@RequestBody Map<String, Long> data) {
    // 즐겨찾기 저장 요청
    return new ResponseEntity<>(new SingleResponseDto<>(settingService.modifyFavorOn(data.get("empId"), data.get("menuId"))), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> modifyFavorOff(@RequestParam Long empId, @RequestParam Long menuId) {
    // 즐겨찾기 삭제 요청
    return new ResponseEntity<>(new SingleResponseDto<>(settingService.modifyFavorOff(empId, menuId)), HttpStatus.OK);
  }

  @GetMapping("/menu/all")
  public ResponseEntity<?> findAllMenu(@RequestParam Long compId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuRes>>(settingService.findAllMenu(compId)), HttpStatus.OK);
  }
}
