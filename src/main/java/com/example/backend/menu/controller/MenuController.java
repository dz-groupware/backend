package com.example.backend.menu.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/GNB")
    public ResponseEntity findMenuByEmpId(@RequestParam Long empId) {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(menuService.findMenuByEmpId(empId)), HttpStatus.OK);
    }

    @GetMapping("/Favor")
    public ResponseEntity findFavorByEmpId(@RequestParam Long empId){
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(menuService.findFavorByEmpId(empId)), HttpStatus.OK);
    }
    @DeleteMapping("/Favor")
    public ResponseEntity removeFavor(@RequestParam Long empId, @RequestParam Long menuId){
        return new ResponseEntity(new SingleResponseDto<Integer>(menuService.removeFavor(empId, menuId)), HttpStatus.OK);
    }

}
