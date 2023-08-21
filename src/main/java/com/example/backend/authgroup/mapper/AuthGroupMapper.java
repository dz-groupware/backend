package com.example.backend.authgroup.mapper;

import com.example.backend.authgroup.dto.response.AuthResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthGroupMapper {
    List<AuthResDto> findAuthList(long companyId, long offset, int limit);
    long getTotalElements();
}
