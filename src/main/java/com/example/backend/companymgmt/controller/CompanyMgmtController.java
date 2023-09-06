package com.example.backend.companymgmt.controller;


import com.example.backend.common.SingleResponseDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.service.CompanyMgmtService;
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


  @PostMapping
  public ResponseEntity addCompanyMgmt(@RequestBody CompanyMgmtReqDto company) {
    companyMgmtService.addCompanyMgmt(company);
    return new ResponseEntity<>(new SingleResponseDto("성공"),
        HttpStatus.CREATED);
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

  @PutMapping("/{id}")
  public ResponseEntity modifyCompanyMgmt(@PathVariable Long id, @RequestBody CompanyMgmtReqDto company) {
    companyMgmtService.modifyCompanyMgmt(id, company);
    return new ResponseEntity<>(new SingleResponseDto("성공"),
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity removeCompanyMgmt(@PathVariable Long id) {
    companyMgmtService.removeCompanyMgmt(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}