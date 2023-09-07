package com.example.backend.menu.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.service.MenuService;
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

  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping("/gnb")
  public ResponseEntity<?> getMenuByEmpId(Long userId, @RequestParam Long empId, Long deptId, Long compId) {
    return new ResponseEntity<>(new SingleResponseDto<List<MenuDto>>(menuService.getMenuByEmpId()), HttpStatus.OK);
  }

  @GetMapping("/favor")
  public ResponseEntity<?> getFavorByEmpId(@RequestParam Long empId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getFavorByEmpId(empId)), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> removeFavor(@RequestParam Long empId, @RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<Integer>(menuService.removeFavor(empId, menuId)), HttpStatus.OK);
  }

  @GetMapping("/lnb")
  public ResponseEntity<?> findMenuByParId(@RequestParam Long menuId, @RequestParam Long compId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.findMenuByParId(menuId, compId)),
        HttpStatus.OK);
  }
}
