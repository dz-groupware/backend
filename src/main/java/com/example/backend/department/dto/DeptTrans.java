package com.example.backend.department.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DeptTrans {
  private Long id = 0L;
  private Long parId = 0L;
  private String name = "";
  private String idTree = "";
  private String nameTree = "";

  public DeptTrans(){}
  public DeptTrans(DeptTrans stream) {
    this.id = stream.id;
    this.parId = stream.parId;
    this.name = new String(stream.name);
    this.idTree = new String(stream.idTree);
    this.nameTree = new String(stream.nameTree);
  }

  public DeptTrans(DeptDto menu){
    this.id = menu.getId();
    this.parId = menu.getParId();
    this.name = menu.getName();
    this.idTree = menu.getIdTree();
    this.nameTree = menu.getNameTree();
  }
}
