package com.example.backend.menu.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

  private final MenuService menuService;

  public MenuController(MenuService menuService, ModalService modalService) {
    this.menuService = menuService;
  }

  @GetMapping("/gnb")
  public ResponseEntity<?> getGnbById() throws JsonProcessingException {
    return new ResponseEntity<>(new SingleResponseDto<List<MenuDto>>(menuService.getGnbById()), HttpStatus.OK);
  }

  @GetMapping("/favor")
  public ResponseEntity<?> getFavorByEmpId() throws JsonProcessingException {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getFavorByEmpId()), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> removeFavor(@RequestParam Long menuId) throws JsonProcessingException {
    return new ResponseEntity<>(
        new SingleResponseDto<Integer>(menuService.removeFavor(menuId)), HttpStatus.OK);
  }

  @GetMapping("/lnb")
  public ResponseEntity<?> getMenuById(@RequestParam Long menuId) throws JsonProcessingException {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getMenuById(menuId)),
        HttpStatus.OK);
  }

  @GetMapping("/gnb/admin")
  public ResponseEntity<?> getGnbByAdmin() throws JsonProcessingException {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuGnb()),
        HttpStatus.OK);
  }

  @GetMapping("/lnb/admin")
  public ResponseEntity<?> getLnbByAdmin(@RequestParam Long menuId) throws JsonProcessingException {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuLnb(menuId)),
        HttpStatus.OK);
  }
}
