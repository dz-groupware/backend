package com.example.backend.company.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.Page;
import com.example.backend.common.SingleResponseDto;
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

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }


  @GetMapping("/{id}")
  public ResponseEntity getCompanyAuthSummaryPage(@PathVariable("id") Long companyId,
      int pageNumber, int pageSize) {
    Page page = companyService.findCompanyAuthSummaryPage(companyId, pageNumber, pageSize);

    return new ResponseEntity(new MultiResponseDto(
        page.getData(),
        page.getPageInfo()
    ), HttpStatus.OK);
  }

  @GetMapping("/{company-id}/auth-summaries")
  public ResponseEntity getCompanyAuthSummaryList(@PathVariable("company-id") Long companyId,
      @RequestParam(required = false) Long afterAuthId,
      @RequestParam int pageSize) {
    return new ResponseEntity(new SingleResponseDto<>(
        companyService.getCompanyAuthSummaryList(companyId, afterAuthId, pageSize)
    ), HttpStatus.OK);
  }
}
