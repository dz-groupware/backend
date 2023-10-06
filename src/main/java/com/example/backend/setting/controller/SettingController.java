//package com.example.backend.setting.controller;
//
//
//import com.example.backend.common.dto.SingleResponseDto;
//import com.example.backend.setting.dto.Dto;
//import com.example.backend.menu.dto.MenuRes;
//import com.example.backend.setting.service.SettingService;
//import java.util.List;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.InvalidMediaTypeException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/setting")
//public class SettingController {
//  private final SettingService settingService;
//
//  public SettingController(SettingService settingService) {
//    this.settingService = settingService;
//  }
//
//
//  // 사용하는지 확인 필요
//
//  @GetMapping("/menu")
//  public ResponseEntity<?> findMenuDetailById(@RequestParam Long menuId) {
//    return new ResponseEntity<>(
//        new SingleResponseDto<List<MenuRes>>(settingService.findMenuDetailById(menuId)),
//        HttpStatus.OK);
//  }
//
//
//
//  @GetMapping("/menu/all")
//  public ResponseEntity<?> findAllMenu(@RequestParam Long compId) {
//    return new ResponseEntity<>(
//        new SingleResponseDto<>(settingService.findAllMenu(compId)), HttpStatus.OK);
//  }
//
////  @GetMapping("/test/redis-add")
////  public String testRedisAdd(){
////    settingService.testRedisAndJwt();
////    return "";
////  }
//
//
//
//  @GetMapping("/test/redis-modify")
//  public String testRedisModify(){
//    settingService.testRedisModify();
//    return "";
//  }
//
//  @GetMapping("/test/list")
//  public List<Dto> testList(){
//    return settingService.testList();
//  }
//
////  @GetMapping("/test/redis-get")
////  public Map<String, Object> testRead(){
////    return settingService.testGetInfo();
////  }
//
////  @GetMapping("/test/jwt-payload")
////  public ResponseEntity<?> testJwtPayload() throws JsonProcessingException {
////    return new ResponseEntity<> (new SingleResponseDto<JwtDto>(settingService.testJwtPayload()), HttpStatus.OK);
////  }
//}
