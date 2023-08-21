package com.example.backend.authgroup.controller;

import com.example.backend.authgroup.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/AuthGroup")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

}
