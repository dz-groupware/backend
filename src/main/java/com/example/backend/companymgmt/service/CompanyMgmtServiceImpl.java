package com.example.backend.companymgmt.service;

import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import com.example.backend.companymgmt.mapper.CompanyMgmtMapper;
import java.util.List;

import com.example.backend.config.jwt.PkDto;
import com.example.backend.config.jwt.SecurityUtil;

import com.example.backend.employeemgmt.dto.EmployeeMgmtCheckSignUpResultResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtListResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtSignUpReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class CompanyMgmtServiceImpl implements CompanyMgmtService {

    private final CompanyMgmtMapper companyMgmtMapper;

    public CompanyMgmtServiceImpl(CompanyMgmtMapper companyMgmtMapper) {
        this.companyMgmtMapper = companyMgmtMapper;
    }

    @Override
    public List<CompanyMgmtListResDto> getCompanyMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return companyMgmtMapper.getCompanyMgmtList(companyId);
    }
    @Override
    public List<CompanyMgmtListResDto> getOpenedCompanyMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return companyMgmtMapper.getOpenedCompanyMgmtList(companyId);
    }
    @Override
    public List<CompanyMgmtListResDto> getClosedCompanyMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();
        return companyMgmtMapper.getClosedCompanyMgmtList(companyId);
    }


    @Override
    public CompanyMgmtResDto getCompanyDetailsById(Long id) {
        Long companyId = SecurityUtil.getCompanyId();
        return companyMgmtMapper.getCompanyDetailsById(id, companyId);
    }

    @Override
    public List<CompanyMgmtListResDto> getAllCompanyMgmtParList() {
        Long companyId = SecurityUtil.getCompanyId();
        return companyMgmtMapper.getAllCompanyMgmtParList(companyId);
    }

    @Override
    public List<CompanyMgmtListResDto> findCompanyMgmtList(String name, int enabledType) {
        Long companyId = SecurityUtil.getCompanyId();
        if (enabledType == 2) {
            return companyMgmtMapper.findAllCompanyMgmtList(companyId, name);
        }

        Boolean enabled = enabledType == 1 ? true : false;

        return companyMgmtMapper.findCompanyMgmtList(companyId, name, enabled);
    }


    @Override
    @Transactional
    public void addCompanyMgmt(CompanyMgmtReqDto companyMgmt) {
        Long companyId = SecurityUtil.getCompanyId();
        Boolean isDuplicated = companyMgmtMapper.getInfoDuplicated(companyMgmt);

        if (isDuplicated) {
            throw new RuntimeException("Data is duplicated");
        }

        if (companyMgmt.getParId() == null || companyMgmt.getParId().equals("")) {
            companyMgmtMapper.addCompanyMgmt(companyMgmt);
            companyMgmtMapper.addIdTreeAndNameTreeWithLastInsertId(companyMgmt.getName());
        } else {
            companyMgmtMapper.addCompanyMgmt(companyMgmt);
            Map<String, String> originalIdTreeAndNameTree = companyMgmtMapper.findParIdTreeAndNameTreeWithParId(companyMgmt.getParId());
            String parIdTree = originalIdTreeAndNameTree.get("parIdTree");
            String parNameTree = originalIdTreeAndNameTree.get("parNameTree");
            companyMgmtMapper.addIdTreeAndNameTreeWithParIdTree(parIdTree, parNameTree ,companyMgmt.getName());
        }
    }




    @Override
    @Transactional
    public void modifyCompanyMgmt(CompanyMgmtReqDto companyMgmt) {
        //원래의 부모값을 저장
        Long originalParId = companyMgmtMapper.getParIdFromDB(companyMgmt.getId());

//        if (companyMgmt.getClosingDate() != null) {
//            companyMgmtMapper.modifyCompanyMgmtWithClosingDate(companyMgmt);
//            return; // exit the method since we have removed the company
//        }

        int circularCount = companyMgmtMapper.checkCircularReference(companyMgmt.getId(), companyMgmt.getParId());
        if (circularCount > 0) {
            throw new IllegalArgumentException("Circular reference detected! Cannot move company under its own subtree.");
        }


        //여기서 입력받은 값으로 parid를 변경해줌 그래야 부모가 child 갖고있는지 확인할때 본인 안걸림
        companyMgmtMapper.modifyCompanyMgmt(companyMgmt);
        //  DB에서 가져온 parId와 입력된 parId를 비교 // 두 값이 다르면 원하는 로직실행
        if (!Objects.equals(originalParId, companyMgmt.getParId())) {

            //원래 parId를 parId로 갖고있는 다른 데이터가 없다면=자식이 없다면
            Boolean hasChild = companyMgmtMapper.checkParHaveChild(originalParId);
            if (hasChild == null || !hasChild) {
                //childnode 1로 변경
                companyMgmtMapper.doNotHaveChild(originalParId);
            }

            //입력받은 parId가 자기자신이면
            if (companyMgmt.getParId() == null || companyMgmt.getParId().equals("") || companyMgmt.getParId().equals(companyMgmt.getId())) {
                Map<String, String> originalTreeData = companyMgmtMapper.getTreeFromDB(companyMgmt.getId());
                String originalIdTree = originalTreeData.get("originalIdTree");
                String originalNameTree = originalTreeData.get("originalNameTree");
                companyMgmtMapper.updateDoNotHaveParTree(originalIdTree, originalNameTree, companyMgmt.getId(),companyMgmt.getName());
            }
            //parid가 자기자신이 아니면서 바뀌엇다면
            else {
                companyMgmtMapper.haveChildNode(companyMgmt.getParId());
                Map<String, String> originalTreeData = companyMgmtMapper.getTreeFromDB(companyMgmt.getId());
                String originalIdTree = originalTreeData.get("originalIdTree");
                String originalNameTree = originalTreeData.get("originalNameTree");

                Map<String, String> updateTreeData = companyMgmtMapper.findParIdTreeAndNameTreeWithParId(companyMgmt.getParId());
                String parIdTree = updateTreeData.get("parIdTree");
                String parNameTree = updateTreeData.get("parNameTree");

                companyMgmtMapper.updateHaveParTree(originalIdTree, originalNameTree, parIdTree, parNameTree, companyMgmt.getId(),companyMgmt.getName());
            }
        }

    }


    @Override
    public void removeCompanyMgmt(Long id) {
        List<Long> idsToRemove = companyMgmtMapper.findIdAtIdTree(id);

        for(Long removeId : idsToRemove) {
            companyMgmtMapper.removeCompanyMgmt(removeId);
        }
    }
}
