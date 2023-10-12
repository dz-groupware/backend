package com.example.backend.companymgmt.controller;


import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.companymgmt.dto.CompanyMgmtCheckSignUpResDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.dto.CompanyMgmtSignUpReqDto;
import com.example.backend.companymgmt.service.CompanyMgmtService;

import com.example.backend.employeemgmt.dto.EmployeeMgmtCheckSignUpResultResDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtSignUpReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")

public class CompanyMgmtController {

  private final CompanyMgmtService companyMgmtService;

  public CompanyMgmtController(CompanyMgmtService companyMgmtService) {
    this.companyMgmtService = companyMgmtService;
  }



  @GetMapping
  public ResponseEntity getCompanyMgmtList() {
    return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.getCompanyMgmtList()),
            HttpStatus.OK);
  }

  @GetMapping("/nametree")
  public ResponseEntity getCompanyMgmtNameTreeList() {
    return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.getCompanyMgmtNameTreeList()),
            HttpStatus.OK);
  }
  @GetMapping("/open")
  public ResponseEntity getOpenedCompanyMgmtList() {

    return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.getOpenedCompanyMgmtList()),
            HttpStatus.OK);
  }

  @GetMapping("/close")
  public ResponseEntity getClosedCompanyMgmtList() {

    return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.getClosedCompanyMgmtList()),
            HttpStatus.OK);
  }


  @PostMapping
  public ResponseEntity<?> addCompanyMgmt(@RequestBody CompanyMgmtReqDto company) {
    System.out.println(company.toString());
    try {
      companyMgmtService.addCompanyMgmt(company);
      return new ResponseEntity<>(new SingleResponseDto("성공"), HttpStatus.CREATED);
    } catch (RuntimeException e) {
      if (e.getMessage().equals("Data is duplicated")) {
        return new ResponseEntity<>(new SingleResponseDto("Data is duplicated"), HttpStatus.CONFLICT);
      }
      throw e;  // 위의 조건에 해당하지 않는 경우 예외를 다시 던집니다.
    }
  }



  @GetMapping("/{id}")
  public ResponseEntity getCompanyDetailsById(@PathVariable Long id) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(companyMgmtService.getCompanyDetailsById(id)),
        HttpStatus.OK);
  }



  @GetMapping("/company-list")
  public ResponseEntity findCompanyMgmtList(@RequestParam String name, int enabledType) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(companyMgmtService.findCompanyMgmtList(name, enabledType)),
        HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity modifyCompanyMgmt(@RequestBody CompanyMgmtReqDto company) {
    try {
      companyMgmtService.modifyCompanyMgmt(company);
      return new ResponseEntity<>(new SingleResponseDto("성공"), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/del/{id}")
  public ResponseEntity removeCompanyMgmt(@PathVariable Long id) {
    companyMgmtService.removeCompanyMgmt(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  @PostMapping("/signupcheck")
  public ResponseEntity checkSignUp(@RequestBody CompanyMgmtSignUpReqDto companymgmt) {
    CompanyMgmtCheckSignUpResDto result = companyMgmtService.checkSignUp(companymgmt);

    if (result.isFromCheck() && (result.getData() == null || result.getData().isEmpty())) {
      // if 문에서 결과가 나왔지만 데이터가 없는 경우
      return new ResponseEntity<>("No data found from check", HttpStatus.NOT_FOUND);
    } else if (!result.isFromCheck()) {
      // if 문에 들어가지 않은 경우
      return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(new SingleResponseDto<>(result.getData()), HttpStatus.OK);
  }

}