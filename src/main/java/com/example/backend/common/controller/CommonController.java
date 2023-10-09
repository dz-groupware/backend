package com.example.backend.common.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.common.service.CommonService;
import com.example.backend.config.jwt.PkDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {
  private final CommonService commonService;

  public CommonController(CommonService commonService) {
    this.commonService = commonService;
  }

  // 즐겨찾기
  @GetMapping("/favor")
  public ResponseEntity<?> findFavorById(@RequestHeader(name = "menuId") Long menuId){
    return new ResponseEntity<>(new SingleResponseDto<>(commonService.findFavorById(menuId)), HttpStatus.OK);
  }

  @PostMapping("/favor")
  public ResponseEntity<?> modifyFavorOn(@RequestHeader(name = "menuId") Long menuId) {
    return new ResponseEntity<>(new SingleResponseDto<>(commonService.modifyFavorOn(menuId)), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> modifyFavorOff(@RequestHeader(name = "menuId") Long menuId){
    return new ResponseEntity<>(new SingleResponseDto<>(commonService.modifyFavorOff(menuId)), HttpStatus.OK);
  }

}
