package com.example.backend.department.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.DeptTrans;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.department.mapper.DepartmentMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentMapper departmentMapper;

  public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
    this.departmentMapper = departmentMapper;
  }

  // 부서 리스트
  @Override
  public List<DeptListDto> getDepartmentBasicList() {
    return departmentMapper.getDepartmentBasicList(SecurityUtil.getCompanyId());
  }

  @Override
  public List<DeptListDto> getDepartmentById(Long compId, Long parId) {
    if (compId == 0L) {
      return departmentMapper.getDepartmentById(SecurityUtil.getCompanyId(), parId);
    } else {
      return departmentMapper.getDepartmentById(compId, parId);
    }
  }

  // 부서 상세 정보
  @Override
  public DeptDto getBasicDetailById(Long id) {
    return departmentMapper.getBasicDetailById(id);
  }

  @Override
  public List<EmpListDto> getEmpListByDeptId(Long id){
    return departmentMapper.getEmpListByDeptId(id);
  }

  // 부서 추가
  @Override
  @Transactional
  public int addDepartment(DeptDto dept) {
    System.out.println("in addDepartment"+dept.getAbbr());
    System.out.println("getStatus"+dept.getStatus());
    Long compId = SecurityUtil.getCompanyId();
//    try{
      dept.setCompId(compId);
      // 메뉴 추가
      if (dept.getStatus().equals("add")){

        DeptTrans updateTree =  new DeptTrans();
        if (dept.getParId().toString().equals("0")){
          // 상위부서 없음 == 상위메뉴 (id == parId로 저장하기엔 parId가 존재하지 않아서 안됨)
          dept.setParId(1L);
          departmentMapper.addDepartment(dept);
          System.out.println(dept.getId().toString());
          updateTree.setId(dept.getId());
          updateTree.setParId(dept.getId());
          updateTree.setIdTree(dept.getId().toString());
          updateTree.setNameTree(dept.getName());
        } else {
          // 상위부서 찾기
          departmentMapper.addDepartment(dept);
          DeptTrans parDept = departmentMapper.getParDept(dept.getParId());
          departmentMapper.modifyUpperDeptCNY(parDept.getId());
          System.out.println(dept.getId().toString());
          updateTree.setId(dept.getId());
          updateTree.setParId(dept.getParId());
          updateTree.setIdTree(parDept.getIdTree()+">"+dept.getId().toString());
          updateTree.setNameTree(parDept.getNameTree()+">"+dept.getName());
        }
        departmentMapper.modifyDeptTree(updateTree);
      }
      // 메뉴 수정
      if (dept.getStatus().equals("modify")){
        System.out.println("modify start"+dept.getStatus());

        // 수정 로직 수행 (트리 수정)
        DeptTrans updateDept = new DeptTrans(dept);
        DeptTrans preUpdateDept = modifyTree(updateDept);
        dept.setParId(preUpdateDept.getParId());
        dept.setIdTree(preUpdateDept.getIdTree());
        dept.setNameTree(preUpdateDept.getNameTree());
        System.out.println("? :"+dept.getAbbr());
        departmentMapper.modifyDepartment(dept);
      }
      return 1;
//    } catch (Exception e) {
//      return -1;
//    }
  }

  // 부서 수정
  @Override
  @Transactional
  public int modifyDepartment(DeptDto dept){
    departmentMapper.modifyDepartment(dept);
    return 1;
  }

  // 부서 삭제
  @Override
  @Transactional
  public int deleteDepartment(Long id) {
    Long compId = SecurityUtil.getCompanyId();
    departmentMapper.deleteDepartment(compId, id, "%"+id.toString()+">%");
    return 1;
  }

  // 부서 검색
  @Override
  public List<DeptListDto> getOptionCompList() {
    if (Objects.equals(SecurityUtil.getCompanyId(),
        departmentMapper.isHeadCompany(SecurityUtil.getCompanyId()))) {
      return departmentMapper.getOptionCompList(SecurityUtil.getCompanyId()+">%", SecurityUtil.getCompanyId());
    } else {
      return departmentMapper.getOptionCompList("%>"+SecurityUtil.getCompanyId()+"%>", SecurityUtil.getCompanyId());
    }
  }

  @Override
  public List<DeptListDto> findDeptNameAndCode(Long compId, String text){
    return departmentMapper.findDeptNameAndCode(compId, text, text);
  }

  // 부서 코드 중복 확인
  @Override
  public boolean checkDeptCode(String text, Long id) {
    int result = departmentMapper.checkDeptCode(text, id);
    if (id == 0 && result == 0) {
      return true;
    }
    if (id > 0 && result == 1) {
      return true;
    }
    return false;
  }

  // 일괄 등록 (보류)
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

  // 부서 추가/수정 시 수행 될 로직
  public DeptTrans modifyTree(DeptTrans dept) {
    //departmentMapper.modifyDepartment(dept);
    // 상위로 지정한 메뉴가 자신의 하위에 있는지 확인
    if (checkDeptInDept(dept.getId(), dept.getParId())) {
      System.out.println("not in here");
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
      System.out.println("have to in here : "+ dept.getId()+" :: "+ dept.getParId());
      if (Objects.equals(dept.getId(), dept.getParId())) {
        originMenu.setParId(originMenu.getId());

        originMenu.setIdTree(originMenu.getId().toString());
        originMenu.setNameTree(originMenu.getName());

        return originMenu;
      } else {
        System.out.println("move directly");
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
        System.out.println("null ? " +originMenu.getParId());
        return originMenu;
      }
    }
  }

  public boolean checkDeptInDept(Long id, Long parId){
    return departmentMapper.checkDeptInDept(id.toString()+">%",">%" + id.toString() + ">%", parId) != 0;
  }

}
