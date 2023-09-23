package com.example.backend.menu.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import com.example.backend.setting.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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
  public ResponseEntity<?> getGnbById(@CookieValue("JWT") String jwt) {
    return new ResponseEntity<>(new SingleResponseDto<List<MenuDto>>(menuService.getGnbById(new JwtDto(jwt))), HttpStatus.OK);
  }

  @GetMapping("/favor")
  public ResponseEntity<?> getFavorByEmpId(@CookieValue("JWT") String jwt) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getFavorByEmpId(new JwtDto(jwt))), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> removeFavor(@CookieValue("JWT") String jwt, @RequestParam Long menuId){
    return new ResponseEntity<>(
        new SingleResponseDto<Integer>(menuService.removeFavor(new JwtDto(jwt), menuId)), HttpStatus.OK);
  }

  @GetMapping("/lnb")
  public ResponseEntity<?> getMenuById(@CookieValue("JWT") String jwt, @RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getMenuById(new JwtDto(jwt), menuId)),
        HttpStatus.OK);
  }

  @GetMapping("/gnb/admin")
  public ResponseEntity<?> getGnbByAdmin(@CookieValue("JWT") String jwt) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuGnb(new JwtDto(jwt))),
        HttpStatus.OK);
  }

  @GetMapping("/lnb/admin")
  public ResponseEntity<?> getLnbByAdmin(@CookieValue("JWT") String jwt, @RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuLnb(new JwtDto(jwt), menuId)),
        HttpStatus.OK);
  }
}
