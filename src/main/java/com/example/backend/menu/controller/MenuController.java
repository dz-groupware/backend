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
    public ResponseEntity getMenuByEmpId(@RequestParam Long empId) {
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(menuService.getMenuByEmpId(empId)), HttpStatus.OK);
    }

    @GetMapping("/Favor")
    public ResponseEntity getFavorByEmpId(@RequestParam Long empId){
        return new ResponseEntity(new SingleResponseDto<List<MenuRes>>(menuService.getFavorByEmpId(empId)), HttpStatus.OK);
    }
    @DeleteMapping("/Favor")
    public ResponseEntity deleteFavor(@RequestParam Long empId, @RequestParam Long menuId){
        System.out.println("[Controller] empId : "+empId+" || menuId : "+menuId);
        return new ResponseEntity(new SingleResponseDto<Integer>(menuService.deleteFavor(empId, menuId)), HttpStatus.OK);
    }

}
