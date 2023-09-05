package com.example.backend.authgroup.mapper;

import com.example.backend.authgroup.dto.response.AuthResDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

  List<AuthResDto> findAuthList(Long companyId, long offset, int limit);

  long getTotalElements();
}
