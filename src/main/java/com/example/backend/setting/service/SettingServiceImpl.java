package com.example.backend.setting.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.setting.dto.Menu;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.mapper.SettingMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class SettingServiceImpl implements SettingService {

  private final SettingMapper settingMapper;

  public SettingServiceImpl(SettingMapper settingMapper) {
    this.settingMapper = settingMapper;
  }

  @Override
  public List<MenuRes> findGnbList() {
    return settingMapper.findGnbList();
  }

  @Override
  public List<MenuRes> findMenuDetailById(Long menuId) {
    return settingMapper.findMenuDetailById(menuId);
  }

  @Override
  public int saveMenu(MenuRes menu, String type) {
    if (type.equals("1")) {
//      try {
        menu.setCompId(SecurityUtil.getCompanyId());
        menu.setNameTree(menu.getName());
        settingMapper.addMenu(menu);
        // 방금 추가 된 id 가져오기
        Long id = menu.getId();
        System.out.println("saved menu id : "+id);
        // par_id 와 id_tree 수정
        settingMapper.modifyParId(id, id.toString());
        return 1;
//      } catch (Exception e) {
//        return 10;
//      }
    }
    if (type.equals("2")) {
      return settingMapper.modifyMenuById(menu);
    }
    if (type.equals("3")) {
      System.out.println("type 3 : "+menu.getParId());
      // 부모 호출
      MenuRes parMenu = settingMapper.getUpperMenuById(menu.getParId());
      menu.setNameTree(parMenu.getNameTree()+">"+menu.getName());
      menu.setCompId(SecurityUtil.getCompanyId());
      settingMapper.addMenu(menu);
      Long id = menu.getId();
      settingMapper.modifyMenuParId(menu.getParId(), id, parMenu.getIdTree()+">"+id);
      settingMapper.modifyUpperMenu(menu.getParId());

      return 1;
    }
    if (type.equals("4")) {
      System.out.println("::"+menu.getParId()+"::"+menu.getId());
      // 부모 호출
      MenuRes parMenu = settingMapper.getUpperMenuById(menu.getParId());
      menu.setCompId(SecurityUtil.getCompanyId());
      menu.setNameTree(parMenu.getNameTree()+">"+menu.getName());
      menu.setIdTree(parMenu.getIdTree()+">"+menu.getId());
      System.out.println("::"+menu.getNameTree() + menu.getIdTree());
      settingMapper.modifyMenuById(menu);
      settingMapper.modifyUpperMenu(menu.getParId());
      return 1;
    }
    return 10;
  }

  @Override
  public List<MenuRes> findMenuByName(String gnbName, String name) {
    return settingMapper.findMenuByName(gnbName, name);
  }

  @Override
  public List<String> findAllIcon() {
    return settingMapper.findAllIcon();
  }

  // 즐겨찾기
  @Override
  public String findFavorById(Long empId, Long menuId) {
    int check = settingMapper.findFavorById(empId, menuId);
    if (check == 0) {
      return "false";
    }
    if (check == 1) {
      return "true";
    }
    // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
    modifyFavorOff(empId, menuId);
    return "false";
  }

  @Override
  public int modifyFavorOn(Long empId, Long menuId) {
    return settingMapper.modifyFavorOn(empId, menuId);
  }

  @Override
  public int modifyFavorOff(Long empId, Long menuId) {
    return settingMapper.modifyFavorOff(empId, menuId);
  }

  @Override
  public List<MenuRes> findAllMenu(Long compId) {
    return settingMapper.findAllMenu(compId);
  }

  // 메뉴 삭제
  @Override
  public int deleteMenu(Long menuId) {
    // id_tree를 이용해서, 모두 deleted_yn = 0
    List<Long> menuIdList = settingMapper.getMenuIdByIdTree("%"+menuId.toString()+"%");

    for (int i=0; i<menuIdList.size(); i++){
      settingMapper.deleteMenu(menuIdList.get(i));
    }
    return 1;
  }
}
