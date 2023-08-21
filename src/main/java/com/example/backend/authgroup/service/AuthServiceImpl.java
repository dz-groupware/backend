package com.example.backend.authgroup.service;

import com.example.backend.authgroup.mapper.AuthGroupMapper;
import com.example.backend.authgroup.dto.response.AuthResDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthGroupMapper authMapper;

    public AuthServiceImpl(AuthGroupMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public List<AuthResDto> findAuthList(long companyId, int pageNumber, int pageSize) {
        long offset = (pageNumber - 1) * pageSize;
        List<AuthResDto> responseList = authMapper.findAuthList(companyId,offset,pageSize);
        return authMapper.findAuthList(companyId,offset,pageSize);
    }

    @Override
    public long getTotalElements() {
        return authMapper.getTotalElements();
    }
}
