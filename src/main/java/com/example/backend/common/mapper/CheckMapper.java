package com.example.backend.common.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckMapper {
  boolean checkMaster (Long empId);
  List<Long> checkExits(Long userId, Long empId, Long deptId, Long compId);

}
