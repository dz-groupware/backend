package com.example.backend.setting.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.setting.dto.Menu;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import com.example.backend.setting.mapper.SettingMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    // 대메뉴 추가
    if (type.equals("1")) {
      try {
        menu.setCompId(SecurityUtil.getCompanyId());
        menu.setNameTree(menu.getName());
        settingMapper.addMenu(menu);
        // 방금 추가 된 id 가져오기
        Long id = menu.getId();
        // par_id 와 id_tree 수정
        settingMapper.modifyParId(id, id.toString());
        return 1;
      } catch (Exception e) {
        return -1;
      }
    }
    // 대메뉴 수정
    if (type.equals("2")) {
      try {
        menu.setParId(menu.getId());
        menu.setCompId(SecurityUtil.getCompanyId());
        menu.setNameTree(menu.getName());
        menu.setIdTree(menu.getId().toString());

        settingMapper.modifyMenuById(menu);
      } catch (Exception e) {
        return -1;
      }
    }
    // 메뉴 추가
    if (type.equals("3")) {
      try{
        menu.setCompId(SecurityUtil.getCompanyId());
        Long id = menu.getId();

        Long parId = menu.getParId();
        // 상위 메뉴를 선택 안 했을 때
        if (parId == 0) {
          menu.setNameTree(menu.getName());
          settingMapper.addMenu(menu);
          settingMapper.modifyMenuParId(menu.getId(), menu.getId(), menu.getId().toString());
        } else {
          MenuRes parMenu = settingMapper.getUpperMenuById(menu.getParId());
          menu.setNameTree(parMenu.getNameTree()+">"+menu.getName());
          settingMapper.addMenu(menu);
          settingMapper.modifyMenuParId(menu.getParId(), menu.getId(), parMenu.getIdTree()+">"+menu.getId().toString());
          // 호출된 상위 메뉴 childNodeYn 수정
          settingMapper.modifyUpperMenu(menu.getParId());
        }
        return 1;
      } catch (Exception e) {
        return -1;
      }
    }
    // 메뉴 수정
    if (type.equals("4")) {
      // 수정 되는
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

  public boolean checkMenuInMenu(Long id, Long parId){
    System.out.println(settingMapper.checkMenuInMenu("%"+id.toString()+"%", parId) != 0);
    if(settingMapper.checkMenuInMenu("%"+id.toString()+"%", parId) != 0) {
      return true;
    }
    return false;
  }

  public MenuTrans getParIdOfUpperMenu(Long id){
    return settingMapper.getParIdOfUpperMenu(id);
  }

  public List<MenuTrans> getPreMoveMenuList(String id){
    return settingMapper.getPreMoveMenuList(id);
  }

  public void modifyPreMoveMenu(MenuTrans menu){
    settingMapper.modifyPreMoveMenu(menu);
  }

  @Override
  public void modifyMenu(){

    // IT가 빅데이터 부서를 상위부서로 변경한 경우
    String type = "4";
    MenuRes menu = new MenuRes();
    menu.setId(53L);
    menu.setParId(54L);
    menu.setName("데이터분석/빅데이터");
    menu.setSortOrder(23);
    menu.setEnabledYN(1);

    if (type.equals("4")) {
      // 상위로 지정한 메뉴가 자신의 하위에 있는지 확인
      System.out.print("modify menu :");
      if (checkMenuInMenu(menu.getId(), menu.getParId())) {
        // 그렇다면 메뉴를 자신보다 상위로 이동시키는 과정 먼저 실행
        // 어디 아래로 이동시킬지 찾기 이동 시킨곳 childNodeYn 변경 필요
        // 실제 A보다 상위 (만약 A가 최상단이라면 자신의 id를 par_id로 넘겨주게 되므로 id=par_id는 제외한다.)
        MenuTrans parMenu = settingMapper.getParIdOfUpperMenu(menu.getId());
        // 이동시킬 메뉴를 찾고 (par_id, id_tree, name_tree 변경) 하위 모든 메뉴도 변경
        String originIdTree;
        String originNameTree;

        if(parMenu == null){
          System.out.println("parMenu is null");
          // 만약 상위 메뉴가 없어 상위메뉴의 정보를 사용할 수 없다면,
          // 이동시키는 메뉴를 대메뉴 처럼 만든다.
          parMenu = settingMapper.getParMenu(menu.getParId());
          originIdTree = parMenu.getIdTree()+">";
          originNameTree = parMenu.getNameTree()+">";
          parMenu.setParId(parMenu.getId());
          parMenu.setIdTree(parMenu.getId().toString());
          parMenu.setNameTree(parMenu.getName());
        } else {
          // 상위에 메뉴가 있으므로 그 메뉴의 정보를 받아옴
          parMenu = settingMapper.getParMenu(menu.getParId());
          MenuTrans preParMenu = settingMapper.getPreParMenu(menu.getId());
          originIdTree = parMenu.getIdTree()+">";
          originNameTree = parMenu.getNameTree()+">";
          parMenu.setParId(preParMenu.getId());
          parMenu.setIdTree(preParMenu.getIdTree()+">"+parMenu.getId().toString());
          parMenu.setNameTree(preParMenu.getNameTree()+">"+parMenu.getName());
        }

        System.out.println("upper of origin (parMenu) : "+ parMenu.getId()+"::"+ parMenu.getParId()+"::"+parMenu.getName()+"::"+parMenu.getIdTree()+"::"+parMenu.getNameTree()+"::");

        List<MenuTrans> preMenuList = settingMapper.getPreMoveMenuList("%" + menu.getParId().toString() + "%");
        System.out.println("found preMenuList (size) : "+ preMenuList.size());
        for (int i = 0; i < preMenuList.size(); i++) {
          MenuTrans preMenu = preMenuList.get(i);
          if (Objects.equals(preMenu.getId(), parMenu.getParId())) {
            System.out.print("parMenu was null this excange ");
            System.out.println("parMenu : "+ parMenu.getId()+"::"+ parMenu.getParId()+"::"+parMenu.getName()+"::"+parMenu.getIdTree()+"::"+parMenu.getNameTree()+"::");
            // parMenu가 null이 였을 경우 이미 수정 되었으므로 바꾸지 않는다.
            preMenu = parMenu;
            settingMapper.modifyPreMoveMenu(preMenu);
            continue;
          }
          if(Objects.equals(originIdTree.length(), preMenu.getIdTree().length())){
            preMenu.setIdTree(parMenu.getIdTree() + ">" + preMenu.getIdTree().substring(originIdTree.length()));
          } else {
            preMenu.setIdTree(parMenu.getIdTree() + ">" + preMenu.getIdTree().substring(originIdTree.length()-1));
          }

          if(Objects.equals(originNameTree.length(), preMenu.getNameTree().length())){
            preMenu.setNameTree(parMenu.getNameTree() + ">" + preMenu.getNameTree().substring(originNameTree.length()));
          } else {
            preMenu.setNameTree(parMenu.getNameTree() + ">" + preMenu.getNameTree().substring(originNameTree.length()-1));
          }

          if (Objects.equals(menu.getParId(), preMenu.getId())) {

            // 이동시키는 메뉴 중에 상위로 지정된 (여기서는 C) 메뉴를 찾으면 parId 변경
            preMenu.setParId(parMenu.getId());
          }
          settingMapper.modifyPreMoveMenu(preMenu);
        }

        // A를 C 아래로 이동 시킴
//        parMenu = settingMapper.getParMenu(menu.getParId());
        System.out.println("upper of new (parMenu) : "+ parMenu.getId()+"::"+ parMenu.getParId()+"::"+parMenu.getName()+"::"+parMenu.getIdTree()+"::"+parMenu.getNameTree()+"::");
        List<MenuTrans> menuList = settingMapper.getPreMoveMenuList(
            "%" + menu.getId().toString() + "%");
        System.out.println("found menuList (size) : "+ menuList.size());
        for (int i = 0; i < menuList.size(); i++) {
          MenuTrans preMenu = menuList.get(i);
          preMenu.setIdTree(parMenu.getIdTree() + ">" + preMenu.getIdTree());
          preMenu.setNameTree(parMenu.getNameTree() + ">" + preMenu.getNameTree());
          if (Objects.equals(menu.getId(), preMenu.getId())) {

            // 이동시키는 메뉴 중에 이동하는 (여기서는 A) 메뉴를 찾으면 parId 변경
            preMenu.setParId(menu.getParId());
          }
          settingMapper.modifyPreMoveMenu(preMenu);
        }

      } else {
        // 아니라면 건너뛰고 실행
        System.out.println("checking is wrong..");
        MenuTrans parMenu = settingMapper.getParIdOfUpperMenu(menu.getParId());
        List<MenuTrans> menuList = settingMapper.getPreMoveMenuList(
            "%" + menu.getId().toString() + "%");
        for (int i = 0; i < menuList.size(); i++) {

          MenuTrans preMenu = menuList.get(i);
          if (Objects.equals(preMenu.getId(), preMenu.getParId())) {
            // parMenu가 null이 였을 경우 이미 수정 되었으므로 바꾸지 않는다.
            continue;
          }
          preMenu.setIdTree(parMenu.getIdTree() + ">" + preMenu.getId().toString());
          preMenu.setNameTree(parMenu.getNameTree() + ">" + preMenu.getName());
          settingMapper.modifyPreMoveMenu(preMenu);
        }
      }
    }
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
