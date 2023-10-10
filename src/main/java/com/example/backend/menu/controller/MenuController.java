package com.example.backend.menu.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.dto.PageDto;
import com.example.backend.menu.dto.RouteDto;
import com.example.backend.menu.service.MenuService;
import com.example.backend.modal.service.ModalService;
import com.example.backend.menu.dto.MenuRes;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<?> getGnbById() {
    return new ResponseEntity<>(new SingleResponseDto<List<MenuDto>>(menuService.getGnbById()), HttpStatus.OK);
  }

  @GetMapping("/favor")
  public ResponseEntity<?> getFavorByEmpId() {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getFavorByEmpId()), HttpStatus.OK);
  }

  @DeleteMapping("/favor")
  public ResponseEntity<?> removeFavor(@RequestParam Long menuId){
    return new ResponseEntity<>(
        new SingleResponseDto<Integer>(menuService.removeFavor(menuId)), HttpStatus.OK);
  }

  @GetMapping("/lnb")
  public ResponseEntity<?> getMenuById(@RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getMenuById(menuId)),
        HttpStatus.OK);
  }

  @GetMapping("/route-list")
  public ResponseEntity<?> getMenuList() {
    return new ResponseEntity<>(
        new SingleResponseDto<List<RouteDto>>(menuService.getMenuList()),
        HttpStatus.OK);
  }

  // setting 에서 이동
  @GetMapping("/lnbs")
  public ResponseEntity<?> findLnb(@RequestParam String gnbName, @RequestParam String name, @RequestParam("pageId") Long pageId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuRes>>(menuService.findLnb(gnbName, name, pageId)),
        HttpStatus.OK);
  }

  @PostMapping("/menu")
  public ResponseEntity<?> saveMenu(@RequestBody MenuRes menu, @RequestParam String type) {
    return new ResponseEntity<>(menuService.saveMenu(menu, type), HttpStatus.OK);
  }

  @DeleteMapping("/menu")
  public ResponseEntity<?> deleteMenu(@RequestParam Long menuId) {
    return new ResponseEntity<>(menuService.deleteMenu(menuId), HttpStatus.OK);
  }
  @DeleteMapping("/menu-lnb")
  public ResponseEntity<?> deleteMenuLnb(@RequestParam Long menuId) {
    return new ResponseEntity<>(menuService.deleteMenuLnb(menuId), HttpStatus.OK);
  }

  // 상위 메뉴 선택 시
  @GetMapping("/gnb/admin")
  public ResponseEntity<?> getGnbByAdmin() {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuGnb()),
        HttpStatus.OK);
  }

  @GetMapping("/lnb/admin")
  public ResponseEntity<?> getLnbByAdmin(@RequestParam Long menuId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<MenuDto>>(menuService.getUpperMenuLnb(menuId)),
        HttpStatus.OK);
  }

  @GetMapping("/gnb-list")
  public ResponseEntity<?> getGnbList() {
    try{
      return new ResponseEntity<>(new SingleResponseDto<>(menuService.getGnbList()),
          HttpStatus.OK);
    } catch (InvalidMediaTypeException e) {
      return new ResponseEntity<>("유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/page")
  public ResponseEntity<?> getPageList() {
    return new ResponseEntity<>(new SingleResponseDto<List<PageDto>>(menuService.getPageList()), HttpStatus.OK);
  }
}
