package com.example.backend.menu.service;

import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.employee.mapper.EmployeeMapper;
import com.example.backend.menu.dto.MenuDto;
import com.example.backend.menu.mapper.MenuMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuMapper menuMapper;
  private final EmployeeMapper employeeMapper;
  public MenuServiceImpl(MenuMapper menuMapper,
      EmployeeMapper employeeMapper) {
    this.menuMapper = menuMapper;
    this.employeeMapper = employeeMapper;
  }

  @Override
  public List<MenuDto> getMenuByEmpId() {
    // 마스터인 경우 :  dept가 null일 수 있음.

    // 토큰에서 정보 가져오기
    Long userId = SecurityUtil.getUserId();
    Long empId = SecurityUtil.getEmployeeId();
    Long compId = SecurityUtil.getCompanyId();
    Long deptId = SecurityUtil.getDepartmentId();
    if(employeeMapper.checkMaster(empId)){
      return menuMapper.getMenuByEmpIdForMaster(compId);
    }

    // 유효한 사원/부서/회사인지 확인
    List<Long> result = menuMapper.check(userId, empId, compId, deptId);
    if (result.size() == 2 && result.get(0) == 1L) {
      return menuMapper.getMenuByEmpId(empId, compId, deptId);
    }
    return new ArrayList<MenuDto>();
  }



  @Override
  public List<MenuDto> getFavorByEmpId(Long empId) {
    return menuMapper.getFavorByEmpId(empId);
  }

  @Override
  public int removeFavor(Long empId, Long menuId) {
    return menuMapper.removeFavor(empId, menuId);
  }

  @Override
  public List<MenuDto> findMenuByParId(Long menuId, Long compId) {
    return menuMapper.findMenuByParId(menuId, compId);
  }

}
