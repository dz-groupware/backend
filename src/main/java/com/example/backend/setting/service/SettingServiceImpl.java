package com.example.backend.setting.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.setting.dto.MenuRes;
import com.example.backend.setting.dto.MenuTrans;
import com.example.backend.setting.mapper.SettingMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;


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
      try{
        MenuTrans updateMenu = new MenuTrans(menu);
        MenuTrans preUpdateMenu = modifyMenu(updateMenu);
        menu.setParId(preUpdateMenu.getParId());
        menu.setIdTree(preUpdateMenu.getIdTree());
        menu.setNameTree(preUpdateMenu.getNameTree());
        settingMapper.modifyMenuById(menu);
        return 1;
      } catch (Exception e){
        return 10;
      }
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
  public String findFavorById(Long menuId) {
    Long empId = SecurityUtil.getEmployeeId();
    int check = settingMapper.findFavorById(empId, menuId);
    if (check == 0) {
      return "false";
    }
    if (check == 1) {
      return "true";
    }
    // 의도하지 않은 상황이므로 관련 모든 데이터를 지우고 false 전달
    modifyFavorOff(menuId);
    return "false";
  }

  @Override
  public int modifyFavorOn(Long menuId) {
    Long empId = SecurityUtil.getEmployeeId();
    return settingMapper.modifyFavorOn(empId, menuId);
  }

  @Override
  public int modifyFavorOff(Long menuId) {
    Long empId = SecurityUtil.getEmployeeId();
    return settingMapper.modifyFavorOff(empId, menuId);
  }

  @Override
  public List<MenuRes> findAllMenu(Long compId) {
    return settingMapper.findAllMenu(compId);
  }

  // 메뉴 삭제
  @Override
  public int deleteMenu(Long menuId) {
    List<Long> menuIdList = settingMapper.getMenuIdByIdTree("%"+menuId.toString()+"%");

    for (int i=0; i<menuIdList.size(); i++){
      settingMapper.deleteMenu(menuIdList.get(i));
    }
    return 1;
  }

  // 테스트 대기, 중복코드 분리 예정
  @Override
  public MenuTrans modifyMenu(MenuTrans menu){
    System.out.println(menu.getName());
    System.out.println(menu.getId());

    //부모 노드 child_node_yn update : parMenu, preMenu로 불러와진 메뉴
    //updateChildNodeYnOfParMenu

    // menu: 입력 정보 / parMenu : menu의 상위 메뉴 / preMenu : menu의 상위가 될 메뉴(menu의 하위 메뉴 중) / originMenu : 수정 전 menu 정보
    // 상위로 지정한 메뉴가 자신의 하위에 있는지 확인
    if (checkMenuInMenu(menu.getId(), menu.getParId())) {
      // preMenu를 이동시키는 과정 먼저 실행 preMenu childNodeYn 변경 필요. 만약 preMenu가 없다면 null 반환
      // 자신이 최 상단인지 확인하는 수단
      MenuTrans originMenu = settingMapper.getParIdOfUpperMenu(menu.getId());
      if(originMenu == null){
        System.out.println("origin is gnb");
        // 만약 상위 메뉴가 없어 상위메뉴의 정보를 사용할 수 없다 -> preMenu를 대메뉴 처럼 만든다.
        List<MenuTrans> preMenuList = settingMapper.getPreMoveMenuList("%" + menu.getParId().toString() + "%");

        // preMenu 묶음 중 root 메뉴 (상위로 선택 된 메뉴를 깊은복사로 가져온다)
        Optional<MenuTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> pre.getId() == menu.getParId())
            .findFirst();

        MenuTrans preMenu = new MenuTrans(preMenuStream.get());
        String originPreIdTree = preMenu.getIdTree();
        String originPreNameTree = preMenu.getNameTree();

        preMenu.setParId(preMenu.getId());
        preMenu.setIdTree(preMenu.getId().toString());
        preMenu.setNameTree(preMenu.getName());

        settingMapper.modifyPreMoveMenu(preMenu);

        // preMenuList
        for (int i = 0; i < preMenuList.size(); i++) {
          MenuTrans menuTrans = preMenuList.get(i);
          if (Objects.equals(menuTrans.getId(), preMenu.getId())) {
            continue;
          }
          String tmp = menuTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          menuTrans.setIdTree(preMenu.getIdTree() +">"+ tmp);
          tmp = menuTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          menuTrans.setNameTree(preMenu.getNameTree() +">"+ tmp);
          settingMapper.modifyPreMoveMenu(menuTrans);
        }

        // menu 이동
        List<MenuTrans> MenuList = settingMapper.getPreMoveMenuList("%" + menu.getId().toString() + "%");
        // MenuList
        for (int i = 0; i < MenuList.size(); i++) {
          MenuTrans menuTrans = MenuList.get(i);

          menuTrans.setIdTree(preMenu.getIdTree()+">"+menuTrans.getIdTree());
          menuTrans.setNameTree(preMenu.getNameTree()+">"+menuTrans.getNameTree());
          settingMapper.modifyPreMoveMenu(menuTrans);
        }
      } else {
        MenuTrans parMenu = settingMapper.getParMenu(originMenu.getParId());

        List<MenuTrans> preMenuList = settingMapper.getPreMoveMenuList("%" + menu.getParId().toString() + "%");
        Optional<MenuTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> pre.getId() == menu.getParId())
            .findFirst();

        MenuTrans preMenu = new MenuTrans(preMenuStream.get());
        String originPreIdTree = preMenu.getIdTree();
        String originPreNameTree = preMenu.getNameTree();
        // 상위 메뉴는 parMenu
        preMenu.setParId(parMenu.getId());
        preMenu.setIdTree(parMenu.getIdTree()+">"+preMenu.getId().toString());
        preMenu.setNameTree(parMenu.getNameTree()+">"+preMenu.getName());

        settingMapper.modifyPreMoveMenu(preMenu);

        for (int i = 0; i < preMenuList.size(); i++) {
          MenuTrans menuTrans = preMenuList.get(i);

          if (Objects.equals(menuTrans.getId(), preMenu.getId())) {
            continue;
          }
          String tmp = menuTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setIdTree(preMenu.getIdTree() + ">" + tmp);
          tmp = menuTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setNameTree(preMenu.getNameTree() + ">" + tmp);
          settingMapper.modifyPreMoveMenu(menuTrans);
        }

        // menu 이동
        List<MenuTrans> MenuList = settingMapper.getPreMoveMenuList("%" + menu.getId().toString() + "%");

        String originIdTree = originMenu.getIdTree();
        String originNameTree = originMenu.getNameTree();

        originMenu.setParId(preMenu.getId());
        originMenu.setIdTree(preMenu.getIdTree()+">"+originMenu.getId().toString());
        originMenu.setNameTree(preMenu.getNameTree()+">"+originMenu.getName());

        settingMapper.modifyPreMoveMenu(originMenu);
        settingMapper.modifyUpperMenu(parMenu.getId());
        settingMapper.modifyUpperMenu(preMenu.getId());
        for (int i = 0; i < MenuList.size(); i++) {
          MenuTrans menuTrans = MenuList.get(i);
          if (Objects.equals(menuTrans.getId(), originMenu.getId())) {
            continue;
          }
          String tmp = menuTrans.getIdTree().substring(originIdTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          menuTrans.setIdTree(originMenu.getIdTree() +">"+ tmp);
          tmp = menuTrans.getNameTree().substring(originNameTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          menuTrans.setNameTree(originMenu.getNameTree() +">"+ tmp);
          settingMapper.modifyPreMoveMenu(menuTrans);
        }
      }
      return originMenu;
    } else {
      // 일반적인 수정 로직
      List<MenuTrans> MenuList = settingMapper.getPreMoveMenuList("%" + menu.getId().toString() + "%");

      MenuTrans originMenu = settingMapper.getParMenu(menu.getId());
      MenuTrans preMenu = settingMapper.getParMenu(menu.getParId());

      String originIdTree = originMenu.getIdTree();
      String originNameTree = originMenu.getNameTree();

      originMenu.setParId(preMenu.getId());

      originMenu.setIdTree(preMenu.getIdTree()+">"+originMenu.getId().toString());
      originMenu.setNameTree(preMenu.getNameTree()+">"+originMenu.getName());

      settingMapper.modifyPreMoveMenu(originMenu);
      settingMapper.modifyUpperMenu(preMenu.getId());
      for (int i = 0; i < MenuList.size(); i++) {
        MenuTrans menuTrans = MenuList.get(i);
        if (Objects.equals(menuTrans.getId(), originMenu.getId())) {
          continue;
        }
        String tmp = menuTrans.getIdTree().substring(originIdTree.length());
        if (tmp.startsWith(">")){
          tmp = tmp.substring(1);
        }
        menuTrans.setIdTree(originMenu.getIdTree() +">"+ tmp);
        tmp = menuTrans.getNameTree().substring(originNameTree.length());
        if (tmp.startsWith(">")){
          tmp = tmp.substring(1);
        }
        menuTrans.setNameTree(originMenu.getNameTree() +">"+ tmp);
        settingMapper.modifyPreMoveMenu(menuTrans);
      }
      return originMenu;
    }
  }
}
