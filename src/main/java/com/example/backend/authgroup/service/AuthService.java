package com.example.backend.authgroup.service;

import com.example.backend.authgroup.dto.response.AuthResDto;

import java.util.List;

public interface AuthService {
    List<AuthResDto> findAuthList(long companyId, int pageNumber, int pageSize);
    long getTotalElements();
}
