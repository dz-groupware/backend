package com.example.backend.common.service;

import com.example.backend.config.jwt.PkDto;

public interface CommonService {
  int findFavorById(Long menuId);
  int modifyFavorOn(Long menuId);
  int modifyFavorOff(Long menuId);
}
