package com.example.backend.companymgmt.mapper;


import com.example.backend.companymgmt.dto.CompanyMgmtListResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtResDto;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMgmtMapper {



  List<CompanyMgmtListResDto> getCompanyMgmtList(Long companyId);
  List<CompanyMgmtListResDto> getAllCompanyMgmtParList(Long companyId);
  CompanyMgmtResDto getCompanyDetailsById(Long id,Long companyId);

  List<CompanyMgmtListResDto> findAllCompanyMgmtList(Long companyId,String name);

  List<CompanyMgmtListResDto> findCompanyMgmtList(Long companyId,String name, Boolean enabledYn);

  void addCompanyMgmt(CompanyMgmtReqDto companyMgmt);
  void addIdTreeAndNameTreeWithLastInsertId(String name);
  Map<String, String> findParIdTreeAndNameTreeWithParId(Long parId);
  void addIdTreeAndNameTreeWithParIdTree(String parIdTree, String parNameTree, String name);





  int checkCircularReference(Long id,Long parId);
  void modifyCompanyMgmt(CompanyMgmtReqDto companyMgmt);
  Boolean checkParHaveChild(Long originalParId);
  void doNotHaveChild(Long originalParId);

  Long getParIdFromDB(Long id);

  Map<String, String> getTreeFromDB(Long id);
  void updateDoNotHaveParTree(String originalIdTree, String originalNameTree, Long id, String name);
  void haveChildNode(Long parId);

  void updateHaveParTree(String originalIdTree, String originalNameTree, String parIdTree, String parNameTree, Long id, String name);

  List<Long> findIdAtIdTree(Long id);

  void removeCompanyMgmt(Long id);




}
