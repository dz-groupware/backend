package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.response.AuthResDto;

import java.util.List;

public interface AuthService {
    List<AuthResDto> findAuthList(Long companyId, int pageNumber, int pageSize);
    long getTotalElements();
}
