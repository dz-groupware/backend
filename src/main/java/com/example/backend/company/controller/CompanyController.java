package com.example.backend.company.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.Page;
import com.example.backend.common.SingleResponseDto;
import com.example.backend.company.mapper.CompanyMapper;
import com.example.backend.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService companyService;
  private final CompanyMapper companyMapper;

  public CompanyController(CompanyService companyService,
      CompanyMapper companyMapper) {
    this.companyService = companyService;
    this.companyMapper = companyMapper;
  }


  @GetMapping("/{id}")
  public ResponseEntity getCompanyAuthSummaryPage(@PathVariable("id") Long companyId,
      int pageNumber, int pageSize) {
    Page page = companyService.getCompanyAuthSummaryPage(companyId, pageNumber, pageSize);

    return new ResponseEntity(new MultiResponseDto(
        page.getData(),
        page.getPageInfo()
    ), HttpStatus.OK);
  }

  @GetMapping("/{company-id}/auth")
  public ResponseEntity getCompanyAuthList(@PathVariable("company-id") Long companyId,
      @RequestParam(required = true) Long lastId,
      @RequestParam(required = true) int pageSize,
      @RequestParam(required = false) String orderBy) {
    return new ResponseEntity(new SingleResponseDto<>(
        companyService.getCompanyAuthList(companyId, lastId, orderBy, pageSize)
    ), HttpStatus.OK);
  }

  @GetMapping("/{company-id}/auth/count")
  public ResponseEntity getCompanyAuthCount(@PathVariable("company-id") Long companyId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) Long employeeId,
      @RequestParam(required = false) String orderBy) {
    return new ResponseEntity(new SingleResponseDto<>(
        companyService.getCompanyAuthCount(companyId, departmentId, employeeId, orderBy)
    ), HttpStatus.OK);
  }

  @GetMapping("/{company-id}/gnb-list")
  public ResponseEntity getCompanyGnbList(@PathVariable("company-id") Long companyId) {
    return new ResponseEntity(new SingleResponseDto<>(
        companyService.getCompanyGnbList(companyId)
    ), HttpStatus.OK);
  }

  @GetMapping("/{company-id}/gnb/{gnb-id}/lnb-list")
  public ResponseEntity getCompanyLnbList(@PathVariable("company-id") Long companyId,
      @PathVariable("gnb-id") Long parId) {
    return new ResponseEntity(new SingleResponseDto<>(
        companyService.getCompanyLnbList(companyId, parId)
    ), HttpStatus.OK);
  }

  @GetMapping("/{company-id}/auth/{auth-id}/menu-list")
  public ResponseEntity getAuthMenuList(@PathVariable("company-id") Long companyId,
      @PathVariable("auth-id") Long authId) {
    return new ResponseEntity(new SingleResponseDto<>(
        companyService.getAuthMenuList(companyId, authId)
    ), HttpStatus.OK);
  }
}
