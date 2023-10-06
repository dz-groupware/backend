package com.example.backend.redis;

import com.example.backend.config.jwt.PkDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedisMapper {
  List<Long> findMenuId(Long empId, Long deptId, Long compId);
  PkDto getAllKeys(Long empId);
  boolean checkMaster(Long empId);
}
