package com.example.backend.menu.mapper;

import com.example.backend.menu.dto.MenuDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

  List<MenuDto> getMenuByEmpId(Long empId);

  List<MenuDto> findFavorByEmpId(Long empId);

  int removeFavor(Long empId, Long menuId);

  List<MenuDto> findMenuByParId(Long menuId, Long compId);

  List<Long> check(Long userId, Long empId, Long deptId, Long compId);
}
