package com.example.backend.menu.mapper;

import com.example.backend.menu.dto.MenuDto;
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
}
