package com.example.backend.companymgmt.controller;


import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.service.CompanyMgmtService;
import com.example.backend.config.jwt.PkDto;
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
    companyMgmtService.modifyCompanyMgmt(company);
    return new ResponseEntity<>(new SingleResponseDto("성공"),
        HttpStatus.OK);
  }

  @DeleteMapping("/del/{id}")
  public ResponseEntity removeCompanyMgmt(@PathVariable Long id) {
    companyMgmtService.removeCompanyMgmt(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}