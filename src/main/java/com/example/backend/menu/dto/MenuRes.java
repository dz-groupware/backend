package com.example.backend.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRes {
    private Long id = 0L;
    private Long parId = 0L;
    private String name = "";
    private String iconUrl = "";
    private int sortOrder = 0;
    private String nameTree = "";
    private int childNodeYn = 0;
}
