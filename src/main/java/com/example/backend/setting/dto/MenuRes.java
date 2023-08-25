package com.example.backend.setting.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuRes {

    private Long id = 0L;
    private Long parId = 0L;
    private String name = "0";
    private int enabledYN = 0;
    private String iconUrl = "0";
    private int sortOrder = 0;
    private String nameTree = "0";
    private String idTree = "0";
    private int childNodeYN=0;
    private int adminYN=0;

}
