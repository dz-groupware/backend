//package com.example.backend.authgroup.controller;
//
//import com.example.backend.authgroup.service.AuthService;
//import com.example.backend.common.MultiResponseDto;
//import com.example.backend.common.PageDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/AuthGroup")
//public class AuthController {
//
//  private final AuthService authService;
//
//  public AuthController(AuthService authService) {
//    this.authService = authService;
//  }
//
//  @GetMapping
//  public ResponseEntity<?> getPage(@RequestParam Long companyId, @RequestParam int pageNumber,
//      @RequestParam int pageSize) {
//    return new ResponseEntity<>(
//        new MultiResponseDto<>(authService.getAuthList(companyId, pageNumber, pageSize),
//            new PageDto(pageNumber, pageSize, authService.getTotalElements())), HttpStatus.OK);
//  }
//}
