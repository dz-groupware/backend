package com.example.backend.menu.service;

import com.example.backend.common.mapper.CheckMapper;
import com.example.backend.menu.dto.GnbDetailDto;
import com.example.backend.menu.dto.Menu;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.dto.PageDto;
import com.example.backend.menu.dto.RouteDto;
import com.example.backend.menu.mapper.MenuMapper;
import com.example.backend.config.jwt.PkDto;
import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.dto.MenuTrans;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;
  private final CheckMapper checkMapper;

  public MenuServiceImpl(MenuMapper menuMapper, CheckMapper checkMapper) {
    this.menuMapper = menuMapper;
    this.checkMapper = checkMapper;
  }

  // GNB
  @Override
  public List<MenuDto> getGnbById(PkDto pkDto) {
    if(pkDto.isMasterYn()) {
      return menuMapper.getGnbForMaster(pkDto.getCompId());
    } else {
      return menuMapper.getGnbByEmpId(pkDto.getEmpId(), pkDto.getCompId(), pkDto.getDeptId());
    }
  }

  // GNB 즐겨찾기
  @Override
  public List<MenuDto> getFavorByEmpId(PkDto pkDto) {
    Long compId = pkDto.getCompId();
    if(pkDto.isMasterYn()) {
      return menuMapper.getFavorForMaster(compId);
    } else {
      return menuMapper.getFavorByEmpId(pkDto.getEmpId(), compId, pkDto.getDeptId());
    }
  }

  // GNB 즐겨찾기 / 삭제
  @Override
  public int removeFavor(PkDto pkDto, Long menuId) {
    return menuMapper.removeFavor(pkDto.getEmpId(), menuId);
  }

  // GNB -> LNB
  @Override
  public List<MenuDto> getMenuById(PkDto pkDto, Long menuId) {
    Long compId = pkDto.getCompId();
    if(pkDto.isMasterYn()) {
      return menuMapper.getMenuForMaster(menuId, compId);
    } else {
      return menuMapper.getMenuById(menuId, pkDto.getEmpId(), compId, pkDto.getDeptId());
    }
  }

  // 라우팅 리스트
  @Override
  public List<RouteDto> getMenuList(PkDto pkDto) {
    if(pkDto.isMasterYn()){
      return menuMapper.getMenuListForMaster(pkDto.getCompId());
    }else{
      return menuMapper.getMenuList(pkDto.getEmpId(), pkDto.getDeptId(), pkDto.getCompId());
    }
  }

  // setting 에서 이동

  @Override
  public List<MenuRes> findLnb(String gnbName, String name) {
    return menuMapper.findMenuByName(gnbName, name);
  }

  // 대메뉴/메뉴 저장/수정
  @Override
  public int saveMenu(PkDto pkDto, MenuRes menu, String type) {
    // 대메뉴 추가
    if (type.equals("1")) {
      try {
        MenuRes saveMenu = new MenuRes();
        saveMenu.setCompId(pkDto.getCompId());
        saveMenu.setParId(menu.getParId());
        saveMenu.setName(menu.getName());
        saveMenu.setNameTree(menu.getName());
        saveMenu.setSortOrder(menu.getSortOrder());
        saveMenu.setEnabledYn(menu.isEnabledYn());
        saveMenu.setIconUrl(menu.getIconUrl());
        menuMapper.addMenu(saveMenu);
        // 방금 추가 된 id 가져오기
        Long id = saveMenu.getId();
        // par_id 와 id_tree 수정
        menuMapper.modifyParId(id, id.toString());
        return 1;
      } catch (Exception e) {
        log.info(e.getMessage());
        return -1;
      }
    }
    // 대메뉴 수정
    if (type.equals("2")) {
      try {
        // 대메뉴 수정
        MenuRes saveMenu = new MenuRes();
        saveMenu.setCompId(pkDto.getCompId());
        saveMenu.setId(menu.getId());
        saveMenu.setParId(menu.getParId());
        saveMenu.setName(menu.getName());
        saveMenu.setSortOrder(menu.getSortOrder());
        saveMenu.setEnabledYn(menu.isEnabledYn());
        saveMenu.setIconUrl(menu.getIconUrl());
        saveMenu.setIdTree(menu.getId().toString());
        saveMenu.setNameTree(menu.getName());
        menuMapper.modifyMenuById(saveMenu);
        // 하위 메뉴의 nameTree 바꿔줘야 함.
        //찾기
        List<MenuTrans> childMenu = menuMapper.findChildAll(menu.getId().toString()+">%");
        MenuTrans saveChild = new MenuTrans();

        for (MenuTrans child : childMenu){
          saveChild.setId(child.getId());
          saveChild.setNameTree(menu.getName()+"/"+child.getNameTree().substring(child.getNameTree().indexOf("/") + 1));
          menuMapper.modifyChildNameTree(saveChild);
        }
      } catch (Exception e) {
        log.error(e.getMessage());
        return -1;
      }
    }
//     메뉴 추가
    if (type.equals("3")) {
      try{
        menu.setCompId(pkDto.getCompId());
        // 상위 메뉴를 선택 안 했을 때 같은거 없음.
        MenuTrans parMenu = menuMapper.getMenuByMenuId(menu.getParId());
        menu.setNameTree(parMenu.getNameTree()+"/"+menu.getName());
        menuMapper.addMenu(menu);
        menuMapper.modifyMenuParId(menu.getParId(), menu.getId(), parMenu.getIdTree()+">"+menu.getId().toString());
        menuMapper.updateChildNodeYn(menu.getParId());
        return 1;
      } catch (Exception e) {
        return -1;
      }
    }
    // 메뉴 수정
    if (type.equals("4")) {
      try{
        // 트리 변경을 위한
        MenuTrans updateMenu = new MenuTrans(menu);
        MenuTrans preUpdateMenu = modifyMenu(updateMenu);
        // 메뉴 수정
        menu.setParId(menu.getParId());
        menu.setIdTree(preUpdateMenu.getIdTree());
        menu.setNameTree(preUpdateMenu.getNameTree());
        menuMapper.modifyMenuById(menu);
        return 1;
      } catch (Exception e){
        return 10;
      }
    }
    return 10;
  }

  // 메뉴 수정 시 트리 수정
  private MenuTrans modifyMenu(MenuTrans menu){
    // menu : 변경될 정보를 담은 메뉴 id: o, par_id: m, name: m, id_tree: o, name_tree: o (origin/modify)
    log.info(menu.getName());

    //부모 노드 child_node_yn update : parMenu, preMenu로 불러와진 메뉴
    //updateChildNodeYnOfParMenu

    // menu: 입력 정보 / parMenu : menu의 상위 메뉴 / preMenu : menu의 상위가 될 메뉴(menu의 하위 메뉴 중) / originMenu : 수정 전 menu 정보
    // 상위로 지정한 메뉴가 자신의 하위에 있는지 확인

    if (menuMapper.checkMenuInMenu("%" + menu.getId().toString() + "%", menu.getParId()) != 0) {
      // 상위가 될 메뉴를 자신 보다 상위로 옮기는 과정 필요

      // preMenu를 이동시키는 과정 먼저 실행 preMenu childNodeYn 변경 필요. 만약 preMenu가 없다면 null 반환
      // 수정 전 메뉴 정보 가져오기
      MenuTrans originMenu = menuMapper.getMenuByMenuId(menu.getId());
      if(Objects.equals(originMenu.getId(), originMenu.getParId())){
        // 상위 메뉴가 없어. 어떤 메뉴의 하위 메뉴로 만들 수 없음. 메뉴 묶음을 이동 시키고, 루트 메뉴를 대메뉴로 만든다.
        // 만약 상위 메뉴가 없어 상위메뉴의 정보를 사용할 수 없다 -> preMenu를 대메뉴 처럼 만든다.
        List<MenuTrans> preMenuList = menuMapper.getMoveMenuList("%" + menu.getParId().toString() + "%");

        // preMenu 묶음 중 root 메뉴 (상위로 선택 된 메뉴를 깊은복사로 가져온다)
        Optional<MenuTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> Objects.equals(pre.getId(), menu.getParId()))
            .findFirst();

        MenuTrans rootMenu = new MenuTrans(preMenuStream.get());
        String originPreIdTree = rootMenu.getIdTree();
        String originPreNameTree = rootMenu.getNameTree();

        // 대메뉴 처럼 바꿔서 저장
        rootMenu.setParId(rootMenu.getId());
        rootMenu.setIdTree(rootMenu.getId().toString());
        rootMenu.setNameTree(rootMenu.getName());
        menuMapper.modifyPreMoveMenu(rootMenu);

        // 메뉴 묶음 id_tree, name_tree 변경
        for (MenuTrans menuTrans : preMenuList) {
          if (Objects.equals(menuTrans.getId(), rootMenu.getId())) {
            continue;
          }
          String tmp = menuTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setIdTree(rootMenu.getIdTree() + ">" + tmp);
          tmp = menuTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith("/")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setNameTree(rootMenu.getNameTree() + "/" + tmp);
          menuMapper.modifyPreMoveMenu(menuTrans);
        }
//        menuMapper.modifyMoveMenuList(preMenuList); // 실험해봐야 함

        // menu 이동
        List<MenuTrans> MenuList = menuMapper.getMoveMenuList("%" + menu.getId().toString() + "%");
        // MenuList
        for (MenuTrans menuTrans : MenuList) {
          menuTrans.setIdTree(originPreIdTree + ">" + menuTrans.getIdTree());
          menuTrans.setNameTree(originPreNameTree + "/" + menuTrans.getNameTree());
          menuMapper.modifyPreMoveMenu(menuTrans);
        }
      } else {
        // rootMenu가 되어줄 Menu
        MenuTrans rootMenu = menuMapper.getMenuByMenuId(originMenu.getParId());
        String originPreIdTree = rootMenu.getIdTree();
        String originPreNameTree = rootMenu.getNameTree();

        List<MenuTrans> preMenuList = menuMapper.getMoveMenuList("%" + menu.getParId().toString() + "%");

        Optional<MenuTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> Objects.equals(pre.getId(), menu.getParId()))
            .findFirst();

        MenuTrans preRootMenu = new MenuTrans(preMenuStream.get());
        preRootMenu.setParId(rootMenu.getId());
        preRootMenu.setIdTree(originPreIdTree+">"+preRootMenu.getId().toString());
        preRootMenu.setNameTree(originPreNameTree+"/"+preRootMenu.getName());
        menuMapper.modifyPreMoveMenu(preRootMenu);

        for (MenuTrans menuTrans : preMenuList) {
          if (Objects.equals(menuTrans.getId(), preRootMenu.getId())) {
            continue;
          }
          String tmp = menuTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setIdTree(originPreIdTree + ">" + tmp);
          tmp = menuTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith("/")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setNameTree(originPreNameTree + "/" + tmp);
          menuMapper.modifyPreMoveMenu(menuTrans);
        }

        // menu 이동
        List<MenuTrans> MenuList = menuMapper.getMoveMenuList("%" + menu.getId().toString() + "%");

        originMenu.setParId(rootMenu.getId());
        originMenu.setIdTree(originPreIdTree+">"+originMenu.getId().toString());
        originMenu.setNameTree(originPreNameTree+"/"+originMenu.getName());

        menuMapper.modifyPreMoveMenu(originMenu);

        for (MenuTrans menuTrans : MenuList) {
          if (Objects.equals(menuTrans.getId(), originMenu.getId())) {
            continue;
          }
          String tmp = menuTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setIdTree(originMenu.getIdTree() + ">" + tmp);
          tmp = menuTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith("/")) {
            tmp = tmp.substring(1);
          }
          menuTrans.setNameTree(originMenu.getNameTree() + "/" + tmp);
//          menuMapper.modifyPreMoveMenu(menuTrans);
        }

      }
      return originMenu;
    } else {
      // 일반적인 수정 로직
      MenuTrans preMenu = menuMapper.getMenuByMenuId(menu.getParId());

      List<MenuTrans> MenuList = menuMapper.getMoveMenuList("%" + menu.getId().toString() + "%");

      MenuTrans originMenu = menuMapper.getMenuByMenuId(menu.getId());
      String originIdTree = originMenu.getIdTree();
      String originNameTree = originMenu.getNameTree();

      originMenu.setParId(preMenu.getId());
      originMenu.setIdTree(preMenu.getIdTree()+">"+originMenu.getId().toString());
      originMenu.setNameTree(preMenu.getNameTree()+"/"+originMenu.getName());

      menuMapper.modifyPreMoveMenu(originMenu);
      menuMapper.updateChildNodeYn(preMenu.getId());

      for (MenuTrans menuTrans : MenuList) {
        if (Objects.equals(menuTrans.getId(), originMenu.getId())) {
          continue;
        }
        String tmp = menuTrans.getIdTree().substring(originIdTree.length());
        if (tmp.startsWith(">")) {
          tmp = tmp.substring(1);
        }
        menuTrans.setIdTree(originMenu.getIdTree() + ">" + tmp);
        tmp = menuTrans.getNameTree().substring(originNameTree.length());
        if (tmp.startsWith("/")) {
          tmp = tmp.substring(1);
        }
        menuTrans.setNameTree(originMenu.getNameTree() + "/" + tmp);
        menuMapper.modifyPreMoveMenu(menuTrans);
      }
      return originMenu;
    }
  }

  // 메뉴 설정 페이지 : 상위 메뉴 선택 모달
  @Override
  public List<MenuDto> getUpperMenuGnb(PkDto pkDto) {
    return menuMapper.getUpperMenuGnb(pkDto.getCompId());
  }

  @Override
  public List<MenuDto> getUpperMenuLnb(PkDto pkDto, Long menuId) {
    return menuMapper.getUpperMenuLnb(menuId, pkDto.getCompId());
  }

  // 메뉴 설정 페이지 : GNB 리스트
  @Override
  public List<GnbDetailDto> getGnbList(PkDto pkDto) {
    return menuMapper.getGnbList(pkDto.getCompId());
  }


  // 메뉴 삭제
  @Override
  public int deleteMenu(Long menuId) {
    List<Long> menuIdList = menuMapper.findChildAllMenu(menuId.toString()+">%", menuId);

    for (Long childId : menuIdList) {
      menuMapper.deleteMenu(childId);
    }
    return 1;
  }

  @Override
  public int deleteMenuLnb(Long menuId) {
    List<Long> menuIdList = menuMapper.findChildAllMenu("%>"+menuId.toString()+">%", menuId);

    for (Long childId : menuIdList) {
      menuMapper.deleteMenu(childId);
    }
    return 1;
  }

  @Override
  public List<PageDto> getPageList(){
    return menuMapper.getPageList();
  }
}
