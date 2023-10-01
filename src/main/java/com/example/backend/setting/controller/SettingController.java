package com.example.backend.setting.controller;


import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.config.jwt.PkDto;
import com.example.backend.setting.dto.Dto;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.service.SettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting")
public class SettingController {
  private final SettingService settingService;

  public SettingController(SettingService settingService) {
    this.settingService = settingService;
  }

  @GetMapping("/menu/gnb")
  public ResponseEntity<?> findGnbList() {
    try{
      return new ResponseEntity<>(new SingleResponseDto<>(settingService.findGnbList()),
          HttpStatus.OK);
    } catch (InvalidMediaTypeException e) {
      return new ResponseEntity<>("유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/menu")
  public ResponseEntity<?> findMenuDetailById(@RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuRes>>(settingService.findMenuDetailById(menuId)),
        HttpStatus.OK);
  }

  @PostMapping("/menu")
  public ResponseEntity<?> saveMenu(@RequestAttribute PkDto pkDto, @RequestBody MenuRes menu, @RequestParam String type) {
    return new ResponseEntity<>(settingService.saveMenu(pkDto, menu, type), HttpStatus.OK);
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
  public ResponseEntity<?> findFavorById(@RequestAttribute PkDto pkDto, @RequestHeader Long menuId){
    // 현재 즐겨찾기 상태를 가져오기
    return new ResponseEntity<>(new SingleResponseDto<>(settingService.findFavorById(pkDto, menuId)), HttpStatus.OK);
  }

  @PostMapping("/favor")
  public ResponseEntity<?> modifyFavorOn(@RequestAttribute PkDto pkDto, @RequestHeader Long menuId) {
    // 즐겨찾기 저장 요청
    return new ResponseEntity<>(new SingleResponseDto<>(settingService.modifyFavorOn(pkDto, menuId)), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> modifyFavorOff(@RequestAttribute PkDto pkDto, @RequestHeader Long menuId){
    // 즐겨찾기 삭제 요청
    return new ResponseEntity<>(new SingleResponseDto<>(settingService.modifyFavorOff(pkDto, menuId)), HttpStatus.OK);
  }

  @GetMapping("/menu/all")
  public ResponseEntity<?> findAllMenu(@RequestParam Long compId) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(settingService.findAllMenu(compId)), HttpStatus.OK);
  }

//  @GetMapping("/test/redis-add")
//  public String testRedisAdd(){
//    settingService.testRedisAndJwt();
//    return "";
//  }


  @GetMapping("/test/redis-modify")
  public String testRedisModify(){
    settingService.testRedisModify();
    return "";
  }

  @GetMapping("/test/list")
  public List<Dto> testList(){
    return settingService.testList();
  }

//  @GetMapping("/test/redis-get")
//  public Map<String, Object> testRead(){
//    return settingService.testGetInfo();
//  }

//  @GetMapping("/test/jwt-payload")
//  public ResponseEntity<?> testJwtPayload() throws JsonProcessingException {
//    return new ResponseEntity<> (new SingleResponseDto<JwtDto>(settingService.testJwtPayload()), HttpStatus.OK);
//  }
}
