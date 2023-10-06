package com.example.backend.setting.service;

import com.example.backend.redis.RedisService;
import com.example.backend.setting.dto.Dto;
import com.example.backend.menu.dto.MenuRes;
import com.example.backend.menu.dto.MenuTrans;
import com.example.backend.setting.mapper.SettingMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SettingServiceImpl implements SettingService {

  private final SettingMapper settingMapper;
  private final RedisService redisService;

  public SettingServiceImpl(SettingMapper settingMapper, RedisService redisService) {
    this.settingMapper = settingMapper;
    this.redisService = redisService;
  }

  // 메뉴 설정 페이지 : GNB
  @Override
  public List<MenuRes> findGnbList() {
    return settingMapper.findGnbList();
  }


  // 메뉴 페이지 : 메뉴 검색

  // 메뉴 설정 페이지 : 메뉴 상세 정보
  @Override
  public List<MenuRes> findMenuDetailById(Long menuId) {
    return settingMapper.findMenuDetailById(menuId);
  }

  @Override
  public List<Dto> testList(){
    List<Dto> dtoList = new ArrayList<>();
    Dto dto1 = new Dto();
    dto1.setName("name1");
    dto1.setAge("age1");
    Dto dto2 = new Dto();
    dto2.setName("name2");
    dto2.setAge("age2");
    dtoList.add(dto1);
    dtoList.add(dto2);

//    System.out.println("insert before ========================");
//    for (int i=0; i<dtoList.size(); i++){
//      System.out.println(i+" || "+dtoList.get(i).getName() + " || "+dtoList.get(i).getAge());
//    }
//    System.out.println("=================================");

    settingMapper.testList(dtoList);

//    System.out.println("insert after ========================");
//    for (int i=0; i< dtoList.size(); i++){
//      System.out.println(i+" || "+ dtoList.get(i).getId()+" || "+ dtoList.get(i).getName() + " || "+ dtoList.get(i).getAge());
//    }
//    System.out.println("=================================");
    return dtoList;
  }

  @Override
  public List<String> findAllIcon() {
    return settingMapper.findAllIcon();
  }

  @Override
  public List<MenuRes> findAllMenu(Long compId) {
    return settingMapper.findAllMenu(compId);
  }

  @Transactional
  public void testRedisModify(){
    redisService.flushDb(0);
  }
}
