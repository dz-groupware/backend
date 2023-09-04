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
    public List<CompanyMgmtResDto> findCompanyMgmtList() {
        return companyMgmtMapper.findCompanyMgmtList();
    }

    @Override
    public CompanyMgmtResDto findCompanyDetailsById(int id) {
        return companyMgmtMapper.findCompanyDetailsById(id);
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
    public void modifyCompanyMgmt(int id,CompanyMgmtReqDto companyMgmt) {
        companyMgmt.setId(id);
        companyMgmtMapper.modifyCompanyMgmt(companyMgmt);
    }

    @Override
    public void removeCompanyMgmt(int id, CompanyMgmtReqDto companyMgmt) {
        companyMgmt.setId(id);
        companyMgmtMapper.removeCompanyMgmt(companyMgmt);

    }
}
