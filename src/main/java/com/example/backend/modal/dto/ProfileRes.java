package com.example.backend.modal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRes {
    private Long id;
    private String name;
    private String loginId;
    private String number;
    private String imageUrl;
    private String email;
    private String nameTree;
    private String position;
    private String lastIp;
    private String compName;
    private String compId;

}
