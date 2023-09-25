package com.example.backend.gnb.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.gnb.dto.BasicResponseDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import com.example.backend.common.dto.PkDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
  public ResponseEntity<?> getBasicInfoById(@RequestAttribute PkDto pkDto){
    return new ResponseEntity<>(new BasicResponseDto<>(
        menuService.getGnbById(pkDto),
        menuService.getFavorByEmpId(pkDto),
        modalService.getAllProfile(pkDto)), HttpStatus.OK);
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getOtherEmpOfUser(@RequestAttribute PkDto pkDto, @RequestParam Long empId)
      throws JsonProcessingException {
    // 요청으로 받은 empId와 토큰 empId가 같은 유저인지 확인하는 과정 필요.
    if (modalService.checkEmpIds(pkDto, empId)) {
      return new ResponseEntity<>(new BasicResponseDto<>(
          menuService.getGnbById(pkDto),
          menuService.getFavorByEmpId(pkDto),
          modalService.getAllProfile(pkDto)), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new SingleResponseDto<String>(""), HttpStatus.OK);
    }
  }
}
