package com.example.backend.authgroup.controller;

import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.common.dto.MultiResponseDto;
import com.example.backend.common.dto.Page;
import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.authgroup.mapper.AuthGroupMapper;
import com.example.backend.authgroup.service.AuthGroupService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<?> findCompanyAuthList(
      @RequestParam(required = false) Long lastId,
      @RequestParam(required = false) String lastAuthName,
      @RequestParam(required = true) int pageSize,
      @RequestParam(required = false) String searchTerm,
      @RequestParam(required = false) String orderBy) throws UnsupportedEncodingException {
    String decodeSearchTerm = searchTerm==null? null : URLDecoder.decode(searchTerm,"UTF-8");
    if (lastAuthName != null) {
      String decodeLastAuthName = URLDecoder.decode(lastAuthName, "UTF-8");
      System.out.println(decodeLastAuthName);
      return new ResponseEntity<>(new SingleResponseDto<>(
          authGroupService.findCompanyAuthListOrderByAuthName(decodeLastAuthName, orderBy, decodeSearchTerm, pageSize)
      ), HttpStatus.OK);
    }
    if (lastId != null) {
      return new ResponseEntity<>(new SingleResponseDto<>(
          authGroupService.findCompanyAuthListOrderById(lastId, orderBy, decodeSearchTerm, pageSize)
      ), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 둘 다 null인 경우 BadRequest 응답을 보냅니다.
  }


  @GetMapping("/companies/auth/count")
  public ResponseEntity<?> getCompanyAuthCount() {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getCompanyAuthCount()
    ), HttpStatus.OK);
  }


  @GetMapping("/companies/gnb-list")
  public ResponseEntity<?> getCompanyGnbList(@RequestParam(required = false) Boolean enabledYn) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getCompanyGnbList(enabledYn)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/gnb/{gnb-id}/lnb-list")
  public ResponseEntity<?> getCompanyLnbList(@PathVariable("gnb-id") Long parId, @RequestParam(required = false) Boolean enabledYn) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getCompanyLnbList(parId, enabledYn)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/auth/{auth-id}/gnb")
  public ResponseEntity<?> getGnbListOfAuth(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getGnbListOfAuth(authId)
    ), HttpStatus.OK);
  }
  @GetMapping("/companies/auth/{auth-id}/gnb/{par-id}")
  public ResponseEntity<?> getLnbListOfAuth(@PathVariable("auth-id") Long authId, @PathVariable("par-id") Long parMenuId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getLnbListOfAuth(authId, parMenuId)
    ), HttpStatus.OK);
  }
  @GetMapping("/companies/auth/{auth-id}/gnb-all")
  public ResponseEntity<?> getGnbListOfAuthWithAll(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getGnbListOfAuthWithAll(authId)
    ), HttpStatus.OK);
  }

  @GetMapping("/companies/auth/{auth-id}/gnb-all/{par-id}")
  public ResponseEntity<?> getGnbListOfAuthWithAll(@PathVariable("auth-id") Long authId, @PathVariable("par-id") Long parId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getLnbListOfAuthWithAll(authId, parId)
    ), HttpStatus.OK);
  }

  @GetMapping("/auth/{auth-id}")
  public ResponseEntity<?> getEmpListOfAuth(@PathVariable("auth-id") Long authId) {
    return new ResponseEntity<>(new SingleResponseDto<>(
        authGroupService.getEmpListOfAuth(authId)
    ),HttpStatus.OK);
  }

  @PostMapping("/auth")
  public ResponseEntity<?> addAuth(@RequestBody AddAuthDto addAuthDto) {
    authGroupService.addAuth(addAuthDto);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}