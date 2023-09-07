package com.example.backend.authgroup.controller;

import com.example.backend.common.MultiResponseDto;
import com.example.backend.common.Page;
import com.example.backend.common.SingleResponseDto;
import com.example.backend.authgroup.mapper.AuthGroupMapper;
import com.example.backend.authgroup.service.AuthGroupService;
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

  private final AuthGroupService authGroupService;

  public AuthGroupController(AuthGroupService authGroupService,
      AuthGroupMapper companyMapper) {
    this.authGroupService = authGroupService;
  }

  /**
   * @deprecated 페이지네이션 사용x
   * */
  @GetMapping("/companies/auth/page")
  public ResponseEntity<?> getCompanyAuthSummaryPage(int pageNumber, int pageSize) {
    Page<?> page = authGroupService.getCompanyAuthSummaryPage(pageNumber, pageSize);
    return new ResponseEntity<>(new MultiResponseDto<>(
        page.getData(),
        page.getPageInfo()
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/auth/list")
  public ResponseEntity<?> findCompanyAuthList(@PathVariable("company-id") Long companyId,
      @RequestParam(required = true) Long lastId,
      @RequestParam(required = true) int pageSize,
      @RequestParam(required = false) String orderBy) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.findCompanyAuthList(lastId, orderBy, pageSize)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/auth/count")
  public ResponseEntity<?> getCompanyAuthCount() {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getCompanyAuthCount()
    ), HttpStatus.OK);
  }


  @GetMapping("/companies/gnb-list")
  public ResponseEntity<?> getCompanyGnbList() {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getCompanyGnbList()
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/gnb/{gnb-id}/lnb-list")
  public ResponseEntity<?> getCompanyLnbList(@PathVariable("gnb-id") Long parId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getCompanyLnbList(parId)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/auth/{auth-id}/gnb")
  public ResponseEntity<?> getGnbListOfAuth(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getGnbListOfAuth(authId)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/auth/{auth-id}/gnb-all")
  public ResponseEntity<?> getGnbListOfAuthWithAll(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getGnbListOfAuthWithAll(authId)
    ), HttpStatus.OK);
  }

  @GetMapping("/auth/{auth-id}")
  public ResponseEntity<?> getEmpListOfAuth(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getEmpListOfAuth(authId)
    ),HttpStatus.OK);
  }
}