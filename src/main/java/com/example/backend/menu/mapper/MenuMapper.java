package com.example.backend.menu.mapper;

import com.example.backend.config.jwt.PkDto;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.dto.RouteDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {
  List<MenuDto> getGnbByEmpId(Long empId, Long compId, Long deptId);
  List<MenuDto> getGnbForMaster(Long compId);
  List<MenuDto> getFavorByEmpId(Long empId, Long compId, Long deptId);
  List<MenuDto> getFavorForMaster(Long compId);
  int removeFavor(Long empId, Long menuId);
  List<MenuDto> getMenuById(Long menuId, Long empId, Long compId, Long deptId);
  List<MenuDto> getMenuForMaster(Long menuId, Long compId);
  List<MenuDto> getUpperMenuGnb(Long compId);
  List<MenuDto> getUpperMenuLnb(Long menuId, Long compId);
  List<MenuDto> getUpperMenuGnbForMaster(Long compId);
  List<MenuDto> getUpperMenuLnbForMaster(Long menuId, Long compId);
  List<RouteDto> getMenuList(Long empId, Long deptId, Long compId);



}
