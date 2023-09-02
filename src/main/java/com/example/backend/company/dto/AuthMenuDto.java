package com.example.backend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthMenuDto {

  private Long auth_id;
  private Long menu_id;
  private Long menu_par_id;
  private String auth_name;
  private String menu_name;
  private String menu_id_tree;
  private String menu_name_tree;
  private boolean child_node_yn;
  private String menu_admin_yn;
}
