package com.example.backend.gnb.controller;

import com.example.backend.gnb.dto.BasicResponseDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("/")
  public ResponseEntity<?> getBasicInfoById() {
    return new ResponseEntity<>(new BasicResponseDto<>(
        menuService.getGnbById(),
        modalService.getAllProfile()), HttpStatus.OK);
  }
}
