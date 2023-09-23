package com.example.backend.department.service;

import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.DeptTrans;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.department.mapper.DepartmentMapper;
import com.example.backend.setting.dto.JwtDto;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentMapper departmentMapper;

  public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
    this.departmentMapper = departmentMapper;
  }

  @Override
  public int addDepartment(JwtDto jwtDto, DeptDto dept) {
    Long compId = jwtDto.getCompId();
    try{
      dept.setCompId(compId);
      // 메뉴 추가
      if (dept.getStatus().equals("add")){
        if (dept.getParId().toString().equals("")){
          // 상위부서 없음
          dept.setParId(dept.getId());
          dept.setIdTree(dept.getId().toString());
          dept.setNameTree(dept.getName());
        } else {
          // 상위부서 찾기
          DeptTrans parDept = departmentMapper.getParDept(dept.getParId());
          dept.setIdTree(parDept.getIdTree()+">"+dept.getId().toString());
          dept.setNameTree(parDept.getNameTree()+">"+dept.getName());
        }
        departmentMapper.addDepartment(dept);
      }
      // 메뉴 수정
      if (dept.getStatus().equals("modify")){
        // 수정 로직 수행 (트리 수정)
        DeptTrans updateDept = new DeptTrans(dept);
        DeptTrans preUpdateDept = modifyTree(updateDept);
        dept.setParId(preUpdateDept.getParId());
        dept.setIdTree(preUpdateDept.getIdTree());
        dept.setNameTree(preUpdateDept.getNameTree());
        departmentMapper.modifyDepartment(dept);
      }
      return 1;
    } catch (Exception e) {
      return -1;
    }
  }

  @Override
  public int addDeptTest(DeptDto dept){
    System.out.println(dept.getParId());
    System.out.println(dept.getParName());
    System.out.println(dept.getId());
    System.out.println(dept.getName());
    System.out.println(dept.getAbbr());
    System.out.println(dept.getCode());
    System.out.println(dept.getIdTree());
    System.out.println(dept.getNameTree());
    System.out.println(dept.getSortOrder());
    System.out.println(dept.getCompId());
    System.out.println(dept.getStatus());
    return 1;
  }



  @Override
  public List<DeptListDto> getDepartmentBasicList(JwtDto jwtDto) {
    return departmentMapper.getDepartmentBasicList(jwtDto.getCompId());
  }

  @Override
  public List<DeptListDto> getDepartmentById(JwtDto jwtDto, Long parId) {
    Long compId = jwtDto.getCompId();
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
  public int deleteDepartment(JwtDto jwtDto, Long id) {
    Long compId = jwtDto.getCompId();
    departmentMapper.deleteDepartment(compId, id, "%"+id.toString()+"%");
    return 1;
  }
  @Override
  public int modifyAllDepartment(List<DeptDto> dept) {
    for (DeptDto deptDto : dept) {
      if (deptDto.getStatus().equals("modify")) {
        // modify 로직 수행
        DeptTrans updateDept = new DeptTrans(deptDto);
        DeptTrans preUpdateDept = modifyTree(updateDept);
        deptDto.setParId(preUpdateDept.getParId());
        deptDto.setIdTree(preUpdateDept.getIdTree());
        deptDto.setNameTree(preUpdateDept.getNameTree());
        departmentMapper.modifyDepartment(deptDto);
      }
      if (deptDto.getStatus().equals("add")) {
        // add 로직 수행
        departmentMapper.addDepartment(deptDto);
      }
    }
    return 1;
  }

  @Override
  public List<DeptListDto> getOptionCompList(JwtDto jwtDto) {
    return departmentMapper.getOptionCompList(jwtDto.getCompId());
  }

  @Override
  public List<DeptListDto> findDeptNameAndCode(Long compId, String text){
    return departmentMapper.findDeptNameAndCode(compId, text, text);
  }

  public boolean checkDeptInDept(Long id, Long parId){
    return departmentMapper.checkDeptInDept("%" + id.toString() + "%", parId) != 0;
  }

  public DeptTrans modifyTree(DeptTrans dept) {
    //departmentMapper.modifyDepartment(dept);
    // 상위로 지정한 메뉴가 자신의 하위에 있는지 확인
    if (checkDeptInDept(dept.getId(), dept.getParId())) {
      DeptTrans originMenu = departmentMapper.getOriginDept(dept.getId());
      if(originMenu == null){
        // 만약 상위 메뉴가 없어 상위메뉴의 정보를 사용할 수 없다 -> preMenu 를 대메뉴 처럼 만든다.
        List<DeptTrans> preMenuList = departmentMapper.getMoveDeptList("%" + dept.getParId().toString() + "%");

        // preMenu 묶음 중 root 메뉴 (상위로 선택 된 메뉴를 깊은복사로 가져온다)
        Optional<DeptTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> Objects.equals(pre.getId(), dept.getParId()))
            .findFirst();

        DeptTrans preMenu = new DeptTrans(preMenuStream.get());
        String originPreIdTree = preMenu.getIdTree();
        String originPreNameTree = preMenu.getNameTree();

        preMenu.setParId(preMenu.getId());
        preMenu.setIdTree(preMenu.getId().toString());
        preMenu.setNameTree(preMenu.getName());

        departmentMapper.modifyDeptTree(preMenu);

        // preMenuList
        for (DeptTrans deptTrans : preMenuList) {
          if (Objects.equals(deptTrans.getId(), preMenu.getId())) {
            continue;
          }
          String tmp = deptTrans.getIdTree().substring(originPreIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          deptTrans.setIdTree(preMenu.getIdTree() + ">" + tmp);
          tmp = deptTrans.getNameTree().substring(originPreNameTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          deptTrans.setNameTree(preMenu.getNameTree() + ">" + tmp);
          departmentMapper.modifyDeptTree(deptTrans);
        }

        // menu 이동
        List<DeptTrans> MenuList = departmentMapper.getMoveDeptList("%" + dept.getId().toString() + "%");
        // MenuList
        for (DeptTrans deptTrans : MenuList) {
          deptTrans.setIdTree(preMenu.getIdTree() + ">" + deptTrans.getIdTree());
          deptTrans.setNameTree(preMenu.getNameTree() + ">" + deptTrans.getNameTree());
          departmentMapper.modifyDeptTree(deptTrans);
        }
      } else {
        DeptTrans parMenu = departmentMapper.getParDept(originMenu.getParId());

        List<DeptTrans> preMenuList = departmentMapper.getMoveDeptList("%" + dept.getParId().toString() + "%");
        Optional<DeptTrans> preMenuStream = preMenuList.stream()
            .filter(pre -> Objects.equals(pre.getId(), dept.getParId()))
            .findFirst();

        DeptTrans preDept = new DeptTrans(preMenuStream.get());
        String originPreIdTree = preDept.getIdTree();
        String originPreNameTree = preDept.getNameTree();
        // 상위 메뉴는 parMenu
        preDept.setParId(parMenu.getId());
        preDept.setIdTree(parMenu.getIdTree()+">"+preDept.getId().toString());
        preDept.setNameTree(parMenu.getNameTree()+">"+preDept.getName());

        departmentMapper.modifyDeptTree(preDept);

        for (DeptTrans deptTrans : preMenuList) {
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
          departmentMapper.modifyDeptTree(deptTrans);
        }

        // menu 이동
        List<DeptTrans> DeptList = departmentMapper.getMoveDeptList("%" + dept.getId().toString() + "%");

        String originIdTree = originMenu.getIdTree();
        String originNameTree = originMenu.getNameTree();

        originMenu.setParId(preDept.getId());
        originMenu.setIdTree(preDept.getIdTree()+">"+originMenu.getId().toString());
        originMenu.setNameTree(preDept.getNameTree()+">"+originMenu.getName());

        departmentMapper.modifyDeptTree(originMenu);
        departmentMapper.modifyUpperDeptCNY(parMenu.getId());
        departmentMapper.modifyUpperDeptCNY(preDept.getId());
        for (DeptTrans deptTrans : DeptList) {
          if (Objects.equals(deptTrans.getId(), originMenu.getId())) {
            continue;
          }
          String tmp = deptTrans.getIdTree().substring(originIdTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          deptTrans.setIdTree(originMenu.getIdTree() + ">" + tmp);
          tmp = deptTrans.getNameTree().substring(originNameTree.length());
          if (tmp.startsWith(">")) {
            tmp = tmp.substring(1);
          }
          deptTrans.setNameTree(originMenu.getNameTree() + ">" + tmp);
          departmentMapper.modifyDeptTree(deptTrans);
        }
      }
      return originMenu;
    } else {
      // 일반적인 수정 로직
      List<DeptTrans> DeptList = departmentMapper.getMoveDeptList("%" + dept.getId().toString() + "%");

      DeptTrans originMenu = departmentMapper.getParDept(dept.getId());
      DeptTrans preDept = departmentMapper.getParDept(dept.getParId());

      String originIdTree = originMenu.getIdTree();
      String originNameTree = originMenu.getNameTree();

      originMenu.setParId(preDept.getId());

      originMenu.setIdTree(preDept.getIdTree()+">"+originMenu.getId().toString());
      originMenu.setNameTree(preDept.getNameTree()+">"+originMenu.getName());

      departmentMapper.modifyDeptTree(originMenu);
      departmentMapper.modifyUpperDeptCNY(preDept.getId());
      for (DeptTrans deptTrans : DeptList) {
        if (Objects.equals(deptTrans.getId(), originMenu.getId())) {
          continue;
        }
        String tmp = deptTrans.getIdTree().substring(originIdTree.length());
        if (tmp.startsWith(">")) {
          tmp = tmp.substring(1);
        }
        deptTrans.setIdTree(originMenu.getIdTree() + ">" + tmp);
        tmp = deptTrans.getNameTree().substring(originNameTree.length());
        if (tmp.startsWith(">")) {
          tmp = tmp.substring(1);
        }
        deptTrans.setNameTree(originMenu.getNameTree() + ">" + tmp);
        departmentMapper.modifyDeptTree(deptTrans);
      }
      return originMenu;
    }
  }
}
