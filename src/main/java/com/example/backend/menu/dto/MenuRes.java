package com.example.backend.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRes {
    private Long empId;
    private Long menuId;
    private Long parId;
    private String name;
    private String iconUrl;
}
