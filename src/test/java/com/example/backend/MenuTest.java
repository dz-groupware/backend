package com.example.backend;

import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class MenuTest {

    @Autowired
    MenuService menuService;

    @Test
    @DisplayName("사원에 맞는 메뉴를 가져오는지 확인하는 테스트")
    void getMenuByEmpId(){
        Long empId = 1L;
        List<MenuRes> result = menuService.getMenuByEmpId(empId);
//        System.out.println(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("사원에 맞는 메뉴를 가져오는지 확인하는 테스트")
    void deleteFavor(){
        Long empId = 1L;
        Long menuId = 1L;
        int result = menuService.deleteFavor(empId, menuId);
        assertThat(result, is(1));
    }
}
