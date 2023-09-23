package com.example.backend.gnb.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.gnb.dto.BasicResponseDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class gnbController {
  private final MenuService menuService;
  private final ModalService modalService;

  public gnbController(MenuService menuService, ModalService modalService) {
    this.menuService = menuService;
    this.modalService = modalService;
  }

  @GetMapping("")
  public ResponseEntity<?> getBasicInfoById(@CookieValue("JWT") String jwt){
    return new ResponseEntity<>(new BasicResponseDto<>(
        menuService.getGnbById(new JwtDto(jwt)),
        menuService.getFavorByEmpId(new JwtDto(jwt)),
        modalService.getAllProfile(new JwtDto(jwt))), HttpStatus.OK);
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getOtherEmpOfUser(@CookieValue("JWT") String jwt, @RequestParam Long empId)
      throws JsonProcessingException {
    // 요청으로 받은 empId와 토큰 empId가 같은 유저인지 확인하는 과정 필요.
    if (modalService.checkEmpIds(new JwtDto(jwt), empId)) {
      return new ResponseEntity<>(new BasicResponseDto<>(
          menuService.getGnbById(new JwtDto(jwt)),
          menuService.getFavorByEmpId(new JwtDto(jwt)),
          modalService.getAllProfile(new JwtDto(jwt))), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new SingleResponseDto<String>(""), HttpStatus.OK);
    }
  }
}
