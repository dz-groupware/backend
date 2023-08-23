//package com.example.backend.authgroup.service;
//
//import com.example.backend.authgroup.mapper.AuthMapper;
//import com.example.backend.authgroup.dto.response.AuthResDto;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AuthServiceImpl implements AuthService{
//    private final AuthMapper authMapper;
//
//    public AuthServiceImpl(AuthMapper authMapper) {
//        this.authMapper = authMapper;
//    }
//
//    @Override
//    public List<AuthResDto> findAuthList(Long companyId, int pageNumber, int pageSize) {
//        int offset = (pageNumber-1) * pageSize;
//        return authMapper.findAuthList(companyId, offset, pageSize);
//    }
//
//    @Override
//    public long getTotalElements() {
//        return authMapper.getTotalElements();
//    }
//}
