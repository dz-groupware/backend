package com.example.backend.companymgmt.controller;


import com.example.backend.common.SingleResponseDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.service.CompanyMgmtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")

public class CompanyMgmtController {

  private final CompanyMgmtService companyMgmtService;

  public CompanyMgmtController(CompanyMgmtService companyMgmtService) {
    this.companyMgmtService = companyMgmtService;
  }

  @GetMapping("/")
  public ResponseEntity findCompanyMgmtList() {
    return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.findCompanyMgmtList()),
        HttpStatus.OK);
  }


  @PostMapping("/{code}")
  public ResponseEntity insert(@RequestBody CompanyMgmtReqDto company) {
    companyMgmtService.addCompanyMgmt(company);
    return new ResponseEntity<>(new SingleResponseDto("성공"),
        HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity findCompanyDetailsById(@PathVariable int id) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(companyMgmtService.findCompanyDetailsById(id)),
        HttpStatus.OK);
  }


  @GetMapping("/search")
  public ResponseEntity searchCompanyMgmt(@RequestParam String name, int enabledYn) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(companyMgmtService.searchCompanyMgmt(name, enabledYn)),
        HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable int id, @RequestBody CompanyMgmtReqDto company) {
    companyMgmtService.modifyCompanyMgmt(id, company);
    return new ResponseEntity<>(new SingleResponseDto("성공"),
        HttpStatus.OK);
  }

  @PutMapping("/del/{code}")
  public ResponseEntity remove(@PathVariable int id, @RequestBody CompanyMgmtReqDto company) {
    companyMgmtService.removeCompanyMgmt(id, company);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}