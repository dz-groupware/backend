package com.example.backend.redis;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedisMapper {
  public List<Long> findMenuId(Long empId, Long deptId, Long compId);
}
