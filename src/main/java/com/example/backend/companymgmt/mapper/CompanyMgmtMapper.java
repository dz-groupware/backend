package com.example.backend.companymgmt.mapper;


import com.example.backend.companymgmt.dto.*;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMgmtMapper {



  List<CompanyMgmtListResDto> getCompanyMgmtList(Long companyId);
  CompanyMgmtResDto getCompanyDetailsById(Long id);

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




  void modifyCompanyMgmtWithClosingDate(CompanyMgmtReqDto companyMgmt);

  Boolean getInfoDuplicated(CompanyMgmtReqDto companyMgmt);

  List<CompanyMgmtListResDto> getOpenedCompanyMgmtList(Long companyId);

  List<CompanyMgmtListResDto> getClosedCompanyMgmtList(Long companyId);

  List<CompanyMgmtTreeListResDto> getCompanyMgmtNameTreeList(Long companyId);

  Boolean checkDuplicates(@Param("dto")CompanyMgmtSignUpReqDto companyMgmt);

  List<CompanyMgmtCeoResDto> checkSignUp(@Param("dto") CompanyMgmtSignUpReqDto companymgmt);

  Long getUserId(@Param("dto")CompanyMgmtReqDto companymgmt);

  void addCompanyMgmtEmployee(@Param("dto")CompanyMgmtReqDto companymgmt, @Param("userId")Long userId);


  void addCompanyMgmtEmployeeCompany( @Param("companyId")Long companyId, @Param("employeeId")Long employeeId);

  void addCompanyMgmtUser(@Param("dto")CompanyMgmtReqDto companymgmt);

  void removeCompanyMgmtCompany(Long removeId);

  List<Long> findEmployeeIdsByCompId(Long removeId);

  void removeCompanyMgmtEmployee(Long empId);

  void removeCompanyMgmtEmployeeCompany(Long removeId);

  List<CompanyMgmtListResDto> findOpenAllCompanyMgmtList(Long companyId, String name);

  List<CompanyMgmtListResDto> findOpenCompanyMgmtList(Long companyId, String name, Boolean enabled);

  List<CompanyMgmtListResDto> findCloseCompanyMgmtList(Long companyId, String name, Boolean enabled);

  List<CompanyMgmtListResDto> findCloseAllCompanyMgmtList(Long companyId, String name);
}
