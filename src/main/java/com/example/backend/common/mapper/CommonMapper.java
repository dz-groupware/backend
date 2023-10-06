package com.example.backend.common.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
  int findFavorById(Long empId, Long menuId);
  int modifyFavorOn(Long empId, Long menuId);
  int modifyFavorOff(Long empId, Long menuId);
}
