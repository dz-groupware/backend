package com.example.backend.menu.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.dto.RouteDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import com.example.backend.config.jwt.PkDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
  public ResponseEntity<?> getGnbById(@RequestAttribute PkDto pkDto) {
    return new ResponseEntity<>(new SingleResponseDto<List<MenuDto>>(menuService.getGnbById(pkDto)), HttpStatus.OK);
  }

  @GetMapping("/favor")
  public ResponseEntity<?> getFavorByEmpId(@RequestAttribute PkDto pkDto) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getFavorByEmpId(pkDto)), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> removeFavor(@RequestAttribute PkDto pkDto, @RequestParam Long menuId){
    return new ResponseEntity<>(
        new SingleResponseDto<Integer>(menuService.removeFavor(pkDto, menuId)), HttpStatus.OK);
  }

  @GetMapping("/lnb")
  public ResponseEntity<?> getMenuById(@RequestAttribute PkDto pkDto, @RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getMenuById(pkDto, menuId)),
        HttpStatus.OK);
  }

  @GetMapping("/gnb/admin")
  public ResponseEntity<?> getGnbByAdmin(@RequestAttribute PkDto pkDto) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuGnb(pkDto)),
        HttpStatus.OK);
  }

  @GetMapping("/lnb/admin")
  public ResponseEntity<?> getLnbByAdmin(@RequestAttribute PkDto pkDto, @RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuLnb(pkDto, menuId)),
        HttpStatus.OK);
  }

  @GetMapping("/menuList")
  public ResponseEntity<?> getMenuList(@RequestAttribute PkDto pkDto) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<RouteDto>>(menuService.getMenuList(pkDto)),
        HttpStatus.OK);
  }
}
