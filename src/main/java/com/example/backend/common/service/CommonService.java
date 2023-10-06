package com.example.backend.common.service;

import com.example.backend.config.jwt.PkDto;

public interface CommonService {
  int findFavorById(PkDto pkDto, Long menuId);
  int modifyFavorOn(PkDto pkDto, Long menuId);
  int modifyFavorOff(PkDto pkDto, Long menuId);
}
