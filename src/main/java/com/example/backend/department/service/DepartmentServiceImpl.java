package com.example.backend.department.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.DeptTrans;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.department.mapper.DepartmentMapper;
import com.example.backend.setting.dto.MenuTrans;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentMapper departmentMapper;

  public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
    this.departmentMapper = departmentMapper;
  }

  @Override
  public int addDepartment(DeptDto dept) {
    System.out.println(dept.getStatus());

    try{
      dept.setCompId(SecurityUtil.getCompanyId());
      if (dept.getStatus().equals("add")){
        departmentMapper.addDepartment(dept);
      }
      if (dept.getStatus().equals("modify")){
        // 수정 로직 수행 (트리 수정)
        modifyTree(dept);
      }
      return 1;
    } catch (Exception e) {
      return -1;
    }
  }

  @Override
  public List<DeptListDto> getDepartmentBasicList(Long compId) {
    return departmentMapper.getDepartmentBasicList(compId);
  }

  @Override
  public List<DeptListDto> getDepartmentById(Long compId, Long parId) {
    return departmentMapper.getDepartmentById(compId, parId);
  }

  @Override
  public DeptDto getBasicDetailById(Long id) {
    return departmentMapper.getBasicDetailById(id);
  }

  @Override
  public List<EmpListDto> getEmpListByDeptId(Long id){
    return departmentMapper.getEmpListByDeptId(id);
  }
  @Override
  public int modifyDepartment(DeptDto dept){
    departmentMapper.modifyDepartment(dept);
    return 1;
  }

  @Override
  public int deleteDepartment(Long id) {
    Long compId = SecurityUtil.getCompanyId();
    departmentMapper.deleteDepartment(compId, id, "%"+id.toString()+"%");
    return 1;
  }
  @Override
  public int modifyAllDepartment( List<DeptDto> dept) {
    for (int i=0; i<dept.size(); i++) {
      if (dept.get(i).getStatus().equals("modify")) {
        // modify 로직 수행
        //departmentMapper.modifyDepartment(dept.get(i));
      }
      if (dept.get(i).getStatus().equals("add")) {
        // add 로직 수행
        // 현재 프론트 수정이 안된 상태여서 보류
        //departmentMapper.addDepartment(dept.get(i));
      }
    }
    return 1;
  }

  @Override
  public List<DeptListDto> getOptionCompList(){
    Long compId = SecurityUtil.getCompanyId();
    return departmentMapper.getOptionCompList(compId);
  }

  @Override
  public List<DeptListDto> findDeptNameAndCode(Long compId, String text){
    return departmentMapper.findDeptNameAndCode(compId, text, text);
  }

  public boolean checkDeptInDept(Long id, Long parId){
    if(departmentMapper.checkDeptInDept("%"+id.toString()+"%", parId) != 0) {
      return true;
    }
    return false;
  }

  public int modifyTree(DeptDto dept) {
    //departmentMapper.modifyDepartment(dept);
    // 상위로 지정한 메뉴가 자신의 하위에 있는지 확인
    if (checkDeptInDept(dept.getId(), dept.getParId())) {
      DeptTrans originMenu = departmentMapper.getOriginDept(dept.getId());
      if(originMenu == null){
        System.out.println("origin is gnb");
        // 만약 상위 메뉴가 없어 상위메뉴의 정보를 사용할 수 없다 -> preMenu를 대메뉴 처럼 만든다.
        List<DeptTrans> preMenuList = departmentMapper.getPreMoveMenuList("%" + dept.getParId().toString() + "%");

        // preMenu 묶음 중 root 메뉴 (상위로 선택 된 메뉴를 깊은복사로 가져온다)
        Optional<DeptTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> pre.getId() == dept.getParId())
            .findFirst();

        DeptTrans preMenu = new DeptTrans(preMenuStream.get());
        String originPreIdTree = preMenu.getIdTree();
        String originPreNameTree = preMenu.getNameTree();

        preMenu.setParId(preMenu.getId());
        preMenu.setIdTree(preMenu.getId().toString());
        preMenu.setNameTree(preMenu.getName());

        departmentMapper.modifyPreMoveMenu(preMenu);

        // preMenuList
        for (int i = 0; i < preMenuList.size(); i++) {
          DeptTrans deptTrans = preMenuList.get(i);
          if (Objects.equals(deptTrans.getId(), preMenu.getId())) {
            continue;
          }
          String tmp = deptTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          deptTrans.setIdTree(preMenu.getIdTree() +">"+ tmp);
          tmp = deptTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          deptTrans.setNameTree(preMenu.getNameTree() +">"+ tmp);
          departmentMapper.modifyPreMoveMenu(deptTrans);
        }

        // menu 이동
        List<DeptTrans> MenuList = departmentMapper.getPreMoveMenuList("%" + dept.getId().toString() + "%");
        // MenuList
        for (int i = 0; i < MenuList.size(); i++) {
          DeptTrans deptTrans = MenuList.get(i);

          deptTrans.setIdTree(preMenu.getIdTree()+">"+deptTrans.getIdTree());
          deptTrans.setNameTree(preMenu.getNameTree()+">"+deptTrans.getNameTree());
          departmentMapper.modifyPreMoveMenu(deptTrans);
        }
      } else {
        DeptTrans parMenu = departmentMapper.getParMenu(originMenu.getParId());

        List<DeptTrans> preMenuList = departmentMapper.getPreMoveMenuList("%" + dept.getParId().toString() + "%");
        Optional<DeptTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> pre.getId() == dept.getParId())
            .findFirst();

        DeptTrans preDept = new DeptTrans(preMenuStream.get());
        String originPreIdTree = preDept.getIdTree();
        String originPreNameTree = preDept.getNameTree();
        // 상위 메뉴는 parMenu
        preDept.setParId(parMenu.getId());
        preDept.setIdTree(parMenu.getIdTree()+">"+preDept.getId().toString());
        preDept.setNameTree(parMenu.getNameTree()+">"+preDept.getName());

        departmentMapper.modifyPreMoveMenu(preDept);

        for (int i = 0; i < preMenuList.size(); i++) {
          DeptTrans deptTrans = preMenuList.get(i);

          if (Objects.equals(deptTrans.getId(), preDept.getId())) {
            continue;
          }
          String tmp = deptTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          deptTrans.setIdTree(preDept.getIdTree() + ">" + tmp);
          tmp = deptTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          deptTrans.setNameTree(preDept.getNameTree() + ">" + tmp);
          departmentMapper.modifyPreMoveMenu(deptTrans);
        }

        // menu 이동
        List<DeptTrans> DeptList = departmentMapper.getPreMoveMenuList("%" + dept.getId().toString() + "%");

        String originIdTree = originMenu.getIdTree();
        String originNameTree = originMenu.getNameTree();

        originMenu.setParId(preDept.getId());
        originMenu.setIdTree(preDept.getIdTree()+">"+originMenu.getId().toString());
        originMenu.setNameTree(preDept.getNameTree()+">"+originMenu.getName());

        departmentMapper.modifyPreMoveMenu(originMenu);
        departmentMapper.modifyUpperMenu(parMenu.getId());
        departmentMapper.modifyUpperMenu(preDept.getId());
        for (int i = 0; i < MenuList.size(); i++) {
          DeptTrans deptTrans = MenuList.get(i);
          if (Objects.equals(deptTrans.getId(), originMenu.getId())) {
            continue;
          }
          String tmp = deptTrans.getIdTree().substring(originIdTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          deptTrans.setIdTree(originMenu.getIdTree() +">"+ tmp);
          tmp = deptTrans.getNameTree().substring(originNameTree.length());
          if (tmp.startsWith(">")){
            tmp = tmp.substring(1);
          }
          deptTrans.setNameTree(originMenu.getNameTree() +">"+ tmp);
          departmentMapper.modifyPreMoveMenu(deptTrans);
        }
      }
      return originMenu;
    } else {
      // 일반적인 수정 로직
      List<DeptTrans> DeptList = departmentMapper.getPreMoveMenuList("%" + dept.getId().toString() + "%");

      DeptTrans originMenu = departmentMapper.getParMenu(dept.getId());
      DeptTrans preDept = departmentMapper.getParMenu(dept.getParId());

      String originIdTree = originMenu.getIdTree();
      String originNameTree = originMenu.getNameTree();

      originMenu.setParId(preDept.getId());

      originMenu.setIdTree(preDept.getIdTree()+">"+originMenu.getId().toString());
      originMenu.setNameTree(preDept.getNameTree()+">"+originMenu.getName());

      departmentMapper.modifyPreMoveMenu(originMenu);
      departmentMapper.modifyUpperMenu(preDept.getId());
      for (int i = 0; i < MenuList.size(); i++) {
        DeptTrans deptTrans = MenuList.get(i);
        if (Objects.equals(deptTrans.getId(), originMenu.getId())) {
          continue;
        }
        String tmp = deptTrans.getIdTree().substring(originIdTree.length());
        if (tmp.startsWith(">")){
          tmp = tmp.substring(1);
        }
        deptTrans.setIdTree(originMenu.getIdTree() +">"+ tmp);
        tmp = deptTrans.getNameTree().substring(originNameTree.length());
        if (tmp.startsWith(">")){
          tmp = tmp.substring(1);
        }
        deptTrans.setNameTree(originMenu.getNameTree() +">"+ tmp);
        departmentMapper.modifyPreMoveMenu(deptTrans);
      }
      return originMenu;
    }
  }


}
