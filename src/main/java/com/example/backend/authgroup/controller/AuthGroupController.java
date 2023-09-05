package com.example.backend.authgroup.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.Page;
import com.example.backend.common.SingleResponseDto;
import com.example.backend.authgroup.mapper.AuthMapper;
import com.example.backend.authgroup.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-group")
public class AuthGroupController {

  private final AuthService authService;
  private final AuthMapper authMapper;

  public AuthGroupController(AuthService authService,
      AuthMapper companyMapper) {
    this.authService = authService;
    this.authMapper = companyMapper;
  }


  @GetMapping("/companies/{company-id}")
  public ResponseEntity<?> getCompanyAuthSummaryPage(@PathVariable("company-id") Long companyId,
      int pageNumber, int pageSize) {
    Page<?> page = authService.getCompanyAuthSummaryPage(companyId, pageNumber, pageSize);

    return new ResponseEntity<>(new MultiResponseDto<>(
        page.getData(),
        page.getPageInfo()
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/{company-id}/auth")
  public ResponseEntity<?> getCompanyAuthList(@PathVariable("company-id") Long companyId,
      @RequestParam(required = true) Long lastId,
      @RequestParam(required = true) int pageSize,
      @RequestParam(required = false) String orderBy) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getCompanyAuthList(companyId, lastId, orderBy, pageSize)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/{company-id}/auth/count")
  public ResponseEntity<?> getCompanyAuthCount(@PathVariable("company-id") Long companyId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) Long employeeId,
      @RequestParam(required = false) String orderBy) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getCompanyAuthCount(companyId, departmentId, employeeId, orderBy)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/{company-id}/gnb-list")
  public ResponseEntity<?> getCompanyGnbList(@PathVariable("company-id") Long companyId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getCompanyGnbList(companyId)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/{company-id}/gnb/{gnb-id}/lnb-list")
  public ResponseEntity<?> getCompanyLnbList(@PathVariable("company-id") Long companyId,
      @PathVariable("gnb-id") Long parId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getCompanyLnbList(companyId, parId)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/{company-id}/auth/{auth-id}/gnb")
  public ResponseEntity<?> getGnbListOfAuth(@PathVariable("company-id") Long companyId,
      @PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getGnbListOfAuth(companyId, authId)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/{company-id}/auth/{auth-id}/gnb-all")
  public ResponseEntity<?> getGnbListOfAuthWithAll(@PathVariable("company-id") Long companyId,
      @PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getGnbListOfAuthWithAll(companyId, authId)
    ), HttpStatus.OK);
  }

  @GetMapping("/auth/{auth-id}")
  public ResponseEntity<?> getEmpListOfAuth(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authService.getEmpListOfAuth(authId)
    ),HttpStatus.OK);
  }
}
