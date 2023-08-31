package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import com.example.backend.companymgmt.mapper.CompanyMgmtMapper;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyMgmtServiceImpl implements CompanyMgmtService {

    private final CompanyMgmtMapper companyMgmtMapper;

    public CompanyMgmtServiceImpl(CompanyMgmtMapper companyMgmtMapper) {
        this.companyMgmtMapper = companyMgmtMapper;
    }

    @Override
    public List<CompanyMgmtResDto> findCompanyMgmtList(boolean deletedYn) {
        return companyMgmtMapper.findCompanyMgmtList(deletedYn);
    }

    @Override
    public CompanyMgmtResDto findCompanyDetailsByCode(String code) {
        return companyMgmtMapper.findCompanyDetailsByCode(code);
    }

    @Override
    public   List<CompanyMgmtResDto> searchCompanyMgmt(String name, int enabledYn){
        if (enabledYn == 2){
            return companyMgmtMapper.searchAllCompanyMgmt(name);
        }
        return companyMgmtMapper.searchCompanyMgmt(name, enabledYn);
    }
    @Override
    public void addCompanyMgmt(CompanyMgmtReqDto companyMgmt) {
        companyMgmtMapper.addCompanyMgmt(companyMgmt);

    }

    @Override
    public void modifyCompanyMgmt(String code,CompanyMgmtReqDto companyMgmt) {
        companyMgmt.setCode(code);
        companyMgmtMapper.modifyCompanyMgmt(companyMgmt);
    }

    @Override
    public void removeCompanyMgmt(String code, CompanyMgmtReqDto companyMgmt) {

        companyMgmt.setCode(code);

        companyMgmtMapper.removeCompanyMgmt(companyMgmt);

    }
}
