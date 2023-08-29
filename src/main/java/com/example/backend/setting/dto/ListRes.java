package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRes {
    private Long id = 0L;
    private String name = "";
    private String iconUrl = "0";
    private int sortOrder = 0;
    private int sub = 0;
}
