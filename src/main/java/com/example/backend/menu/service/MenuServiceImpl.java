package com.example.backend.menu.service;

import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<MenuRes> getMenuByEmpId(Long empId){
        return menuMapper.getMenuByEmpId(empId);
    }

    @Override
    public List<MenuRes> getFavorByEmpId(Long empId){
        return menuMapper.getFavorByEmpId(empId);
    }

    @Override
    public int deleteFavor(Long empId, Long menuId){
        System.out.println("[Impl] empId : "+empId+" || menuId : "+menuId);
        return menuMapper.deleteFavor(empId, menuId);
    }


}
