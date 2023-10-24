package com.example.backend.companymgmt.service;

import java.util.*;
import com.example.backend.aws.service.S3;
import com.example.backend.companymgmt.dto.*;
import com.example.backend.companymgmt.mapper.CompanyMgmtMapper;
import com.example.backend.menu.service.MenuService;
import com.example.backend.config.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyMgmtServiceImpl implements CompanyMgmtService {

    private final CompanyMgmtMapper companyMgmtMapper;
    private final MenuService menuService;
    private final S3 s3;
    public CompanyMgmtServiceImpl(CompanyMgmtMapper companyMgmtMapper,
        MenuService menuService, S3 s3) {
        this.companyMgmtMapper = companyMgmtMapper;
        this.menuService = menuService;
        this.s3 = s3;
    }

    @Override
    public List<CompanyMgmtListWithCompanyIdResDto> getCompanyMgmtList() {
        Long companyId = SecurityUtil.getCompanyId();

        List<CompanyMgmtListResDto> result = companyMgmtMapper.getCompanyMgmtList(companyId);
        List<CompanyMgmtListWithCompanyIdResDto> result2 = new ArrayList<>();
        for(CompanyMgmtListResDto data : result){
            result2.add(new CompanyMgmtListWithCompanyIdResDto(data,companyId));
        }
        return result2;


    }



    @Override
    public List<CompanyMgmtTreeListResDto> getFinalNameTree() {
        // 재귀적 로직 시작
        Long currentCompanyId = SecurityUtil.getCompanyId();
        CompanyMgmtTreeListResDto result = companyMgmtMapper.getNameTreeByCompanyIdForParId(currentCompanyId);


        if(result.getParId()==result.getId()){
            List<CompanyMgmtTreeListResDto> resultList = companyMgmtMapper.getNameTreeByCompanyId(currentCompanyId);
            return resultList;
        }else{
            List<CompanyMgmtTreeListResDto> resultList = companyMgmtMapper.getNameTreeByCompanyId(currentCompanyId);
            CompanyMgmtTreeListResDto parResultList = companyMgmtMapper.getNameTreeByCompanyIdForParId(result.getParId());
            resultList.add(parResultList);
            return resultList;
        }

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
    @Transactional
    public CompanyMgmtCheckSignUpResDto checkSignUp(CompanyMgmtSignUpReqDto companymgmt) {

        if (companyMgmtMapper.checkDuplicates(companymgmt)) {
            List<CompanyMgmtCeoResDto> result = companyMgmtMapper.checkSignUp(companymgmt);
            return new CompanyMgmtCheckSignUpResDto(result, true);
        }
        return new CompanyMgmtCheckSignUpResDto(null, false);
    }


    @Override
    public CompanyMgmtResDto getCompanyDetailsById(Long id) {
        return companyMgmtMapper.getCompanyDetailsById(id);
    }
    @Override
    public List<CompanyMgmtListResDto> findCompanyMgmtList(String name, int enabledType) {
        Long companyId = SecurityUtil.getCompanyId();
        System.out.println("Did u come here?1"+companyId);
        System.out.println("name"+name);
        System.out.println("enabledType"+enabledType);

        if (enabledType == 2) {
            return companyMgmtMapper.findAllCompanyMgmtList(companyId, name);
        }

        Boolean enabled = enabledType == 1 ? true : false;

        return companyMgmtMapper.findCompanyMgmtList(companyId, name, enabled);
    }


    @Override
    public List<CompanyMgmtListResDto> findOpenCompanyMgmtList(String name, int enabledType) {
        Long companyId = SecurityUtil.getCompanyId();
        if (enabledType == 2) {
            return companyMgmtMapper.findOpenAllCompanyMgmtList(companyId, name);
        }

        Boolean enabled = enabledType == 1 ? true : false;

        return companyMgmtMapper.findOpenCompanyMgmtList(companyId, name, enabled);
    }    @Override
    public List<CompanyMgmtListResDto> findCloseCompanyMgmtList(String name, int enabledType) {
        Long companyId = SecurityUtil.getCompanyId();
        if (enabledType == 2) {
            return companyMgmtMapper.findCloseAllCompanyMgmtList(companyId, name);
        }

        Boolean enabled = enabledType == 1 ? true : false;

        return companyMgmtMapper.findCloseCompanyMgmtList(companyId, name, enabled);
    }


    @Override
    @Transactional
    public void addCompanyMgmt(CompanyMgmtReqDto companymgmt) {


        Boolean isDuplicated = companyMgmtMapper.getInfoDuplicated(companymgmt);

        if (isDuplicated) {
            throw new RuntimeException("Data is duplicated");
        }
        Long companyId = null;

        if (companymgmt.getParId() == null || companymgmt.getParId().equals("")) {
            companyMgmtMapper.addCompanyMgmt(companymgmt);
            companyId = companymgmt.getId();
            System.out.println("idcheck Company"+ companymgmt.getId());
            companyMgmtMapper.addIdTreeAndNameTreeWithLastInsertId(companymgmt.getName());
        } else {
            companyMgmtMapper.changeParHaveChild(companymgmt.getParId());
            companyMgmtMapper.addCompanyMgmt(companymgmt);
            companyId = companymgmt.getId();
            System.out.println("idcheck Company2"+ companymgmt.getId());

            Map<String, String> originalIdTreeAndNameTree = companyMgmtMapper.findParIdTreeAndNameTreeWithParId(companymgmt.getParId());
            String parIdTree = originalIdTreeAndNameTree.get("parIdTree");
            String parNameTree = originalIdTreeAndNameTree.get("parNameTree");
            companyMgmtMapper.addIdTreeAndNameTreeWithParIdTree(parIdTree, parNameTree ,companymgmt.getName());
        }


        if(companymgmt.getEmployeeId() != null){
            Long userId = companyMgmtMapper.getUserId(companymgmt);
            System.out.println("idcheck userId1"+ companymgmt.getId());
            companyMgmtMapper.addCompanyMgmtEmployee(companymgmt, userId);
            Long employeeId=companymgmt.getId();
            System.out.println("idcheck employee1"+ companymgmt.getId());
            companyMgmtMapper.addCompanyMgmtEmployeeCompany(companyId,employeeId);
        }
        else{
            companyMgmtMapper.addCompanyMgmtUser(companymgmt);
            System.out.println("idcheck userid2"+ companymgmt.getId());
            Long userId= companymgmt.getId();
            companyMgmtMapper.addCompanyMgmtEmployee(companymgmt, userId);
            System.out.println("idcheck employee2"+ companymgmt.getId());
            Long employeeId=companymgmt.getId();
            companyMgmtMapper.addCompanyMgmtEmployeeCompany(companyId,employeeId);
        }
        //사원회사테이블 추가
        System.out.println("입력되고난뒤" + companymgmt.getId());
        menuService.insertDefaultMenu(companyId);
        // S3 회사 prefix 생성
        s3.createNewPrefix(companyId);

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
        if (!Objects.equals(companyMgmt.getId(), companyMgmt.getParId())) {
            int circularCount = companyMgmtMapper.checkCircularReference(companyMgmt.getId(), companyMgmt.getParId());
            if (circularCount > 0) {
                throw new IllegalArgumentException("Circular reference detected! Cannot move company under its own subtree.");
            }
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
        List<Long> companyIdsToRemove = companyMgmtMapper.findIdAtIdTree(id);

        for(Long removeId : companyIdsToRemove) {
            // 회사를 삭제
            companyMgmtMapper.removeCompanyMgmtCompany(removeId);

            // 삭제될 emp_id 목록 가져오기
            List<Long> employeeIdsToRemove = companyMgmtMapper.findEmployeeIdsByCompId(removeId);

            List<Long> departmentIdsToRemove = companyMgmtMapper.findDepartmentIdsByCompId(removeId);
            // 각 emp_id에 대해 삭제 처리
            for(Long empId : employeeIdsToRemove) {
                companyMgmtMapper.removeCompanyMgmtEmployee(empId);

            }

            for(Long depId : departmentIdsToRemove) {
                companyMgmtMapper.removeCompanyMgmtDepartment(depId);
                companyMgmtMapper.removeCompanyMgmtEmployeeDepartment(depId);
            }
            // 마지막으로 해당 회사의 직원 관계를 삭제
            companyMgmtMapper.removeCompanyMgmtEmployeeCompany(removeId);


        }
    }
}