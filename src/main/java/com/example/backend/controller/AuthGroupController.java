package com.example.backend.controller;

import com.example.backend.common.Page;
import com.example.backend.service.AuthGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/AuthGroup")
public class AuthGroupController {

    private final AuthGroupService authGroupService;

    public AuthGroupController(AuthGroupService authGroupService) {
        this.authGroupService = authGroupService;
    }

    @GetMapping("/list")
    public Page<>
}
