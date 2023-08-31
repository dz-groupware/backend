package com.example.backend.companymgmt.controller;


import com.example.backend.common.SingleResponseDto;
import com.example.backend.companymgmt.dto.CompanyMgmtReqDto;
import com.example.backend.companymgmt.service.CompanyMgmtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")

public class CompanyMgmtController{

    private final CompanyMgmtService companyMgmtService;

    public CompanyMgmtController(CompanyMgmtService companyMgmtService){
        this.companyMgmtService = companyMgmtService;
    }

    @GetMapping("/")
    public ResponseEntity findCompanyMgmtList(@RequestParam boolean deletedYn){
        return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.findCompanyMgmtList(deletedYn)),
                HttpStatus.OK);
    }


    @PostMapping("/{code}")
    public ResponseEntity insert(@RequestBody CompanyMgmtReqDto company) {
        companyMgmtService.addCompanyMgmt(company);
        return new ResponseEntity<>(new SingleResponseDto("성공"),
                HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity findCompanyDetailsByCode(@PathVariable String code){
        return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.findCompanyDetailsByCode(code)),
                HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity searchCompanyMgmt(@RequestParam String name, int enabledYn){
        return new ResponseEntity<>(new SingleResponseDto<>(companyMgmtService.searchCompanyMgmt(name,enabledYn)),
                HttpStatus.OK);
    }

    @PutMapping("/{code}")
    public ResponseEntity update(@PathVariable String code, @RequestBody CompanyMgmtReqDto company) {
        companyMgmtService.modifyCompanyMgmt(code,company);
        return new ResponseEntity<>(new SingleResponseDto("성공"),
                HttpStatus.OK);
    }

    @PutMapping("/del/{code}")
    public ResponseEntity remove(@PathVariable String code,@RequestBody CompanyMgmtReqDto company) {
        companyMgmtService.removeCompanyMgmt(code, company);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}