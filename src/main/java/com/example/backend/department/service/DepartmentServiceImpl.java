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
    Long compId = SecurityUtil.getCompanyId();
//    try{
      dept.setCompId(compId);
      // 메뉴 추가
      if (dept.getStatus().equals("add")){
        System.out.println("request: add");
        departmentMapper.addDepartment(dept);
        if (dept.getParId().toString().equals("0")){
          // 상위부서 없음 == 상위메뉴 (id == parId로 저장하기엔 parId가 존재하지 않아서 안됨)
          modifyBatch(dept.getId(), dept.getParId(), true, false);
        } else {
          modifyBatch(dept.getId(), dept.getParId(), false, false);
        }
      }
      // 메뉴 수정
      if (dept.getStatus().equals("modify")){
        departmentMapper.modifyDepartment(dept);
        System.out.println("request: modify");
        if (dept.getParId().toString().equals("0")){
          modifyBatch(dept.getId(), dept.getParId(), true, false);
        } else {
          modifyTree(new DeptTrans(dept));
        }
      }
      return 1;
//    } catch (Exception e) {
//      return -1;
//    }
  }

  // 부서 수정
  @Override
  @Transactional
  public int modifyDepartmentAndParId(DeptDto dept){
    departmentMapper.modifyDepartmentAndParId(dept);
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

  // 부서 추가/수정 시 수행 될 로직
  private void modifyTree(DeptTrans dept) {
    if (checkDeptInDept(dept.getId(), dept.getParId())) {
      System.out.println("PAR_DEPT IN DEPT");
      DeptTrans originMenu = departmentMapper.getParDept(dept.getId());
      if (Objects.equals(originMenu.getId(), originMenu.getParId())) {
        System.out.println("m 1 - head");
        modifyBatch(dept.getParId(), originMenu.getParId(), true, true);
        modifyBatch(dept.getId(), dept.getParId(), false, true);
      } else {
        System.out.println("m 1 - general");
        modifyBatch(dept.getParId(), originMenu.getParId(), false, true);
        modifyBatch(dept.getId(), dept.getParId(), false, true);
      }
    } else {
      System.out.println("PAR_DEPT NOT IN DEPT");
      if (Objects.equals(dept.getId(), dept.getParId())) {
        System.out.println("m 2 - head");
        modifyBatch(dept.getId(), dept.getParId(), true, true);
      } else {
        System.out.println("m 3 - general");
        modifyBatch(dept.getId(), dept.getParId(), false, true);
      }
    }
  }

  private void modifyBatch (Long id, Long parId, boolean head, boolean batch) {
    DeptTrans originMenu = departmentMapper.getParDept(id);
    Long originParId = originMenu.getParId();
    String originIdTree = originMenu.getIdTree();
    String originNameTree = originMenu.getNameTree();
    if (head || Objects.equals(originMenu.getId(), originMenu.getParId())) {
      originMenu.setParId(originMenu.getId());
      originMenu.setIdTree(originMenu.getId().toString());
      originMenu.setNameTree(originMenu.getName());
    } else {
      DeptTrans parDept = departmentMapper.getParDept(parId);
      originMenu.setParId(parId);
      originMenu.setIdTree(parDept.getIdTree() + ">" + originMenu.getId());
      originMenu.setNameTree(parDept.getNameTree() + ">" + originMenu.getName());
      departmentMapper.modifyUpperDeptCNY(parDept.getId());
    }
    departmentMapper.modifyDeptTree(originMenu);

    int batchLength = 0;
    if (batch) {
      List<DeptTrans> DeptList = departmentMapper.getMoveDeptList(id + ">%", "%>" + id+ ">%", "%>" + id);
      batchLength = DeptList.size();
      System.out.println(batchLength);
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
    if (( departmentMapper.getIsChildNodeYn(originParId+">%", "%>"+originParId+">%") - batchLength ) == 1) {
      System.out.println("modify CYN true : " + originParId);
      departmentMapper.modifyUpperDeptCNN(originParId);
    } else {
      System.out.println("not modify CYN" + departmentMapper.getIsChildNodeYn(originParId+">%", "&>"+originParId+">%"));
    }
    System.out.println(batchLength);
  }

  public boolean checkDeptInDept(Long id, Long parId){
    System.out.println("id: "+id+" || parId : "+parId);
    return departmentMapper.checkDeptInDept(parId, id+">%","%>" + id + ">%","%>" + id) != 0;
  }
}
