package com.example.backend.authgroup.controller;

import com.example.backend.authgroup.dto.AddAuthDto;
import com.example.backend.authgroup.dto.AddEmpAuthDto;
import com.example.backend.authgroup.dto.AuthResponseDto;
import com.example.backend.authgroup.mapper.AuthGroupMapper;
import com.example.backend.authgroup.service.AuthGroupService;
import com.example.backend.common.dto.SingleResponseDto;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
      return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.findCompanyAuthListOrderByAuthName(decodeLastAuthName, orderBy, decodeSearchTerm, pageSize)));
    }
    if (lastId != null) {
      return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.findCompanyAuthListOrderById(lastId, orderBy, decodeSearchTerm, pageSize)));
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/companies/auth/count")
  public ResponseEntity<?> getCompanyAuthCount() {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getCompanyAuthCount()));
  }


  @GetMapping("/companies/gnb-list")
  public ResponseEntity<?> getCompanyGnbList(@RequestParam(required = false) Boolean enabledYn) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getCompanyGnbList(enabledYn)));
  }

  @GetMapping("/companies/gnb/{gnb-id}/lnb-list")
  public ResponseEntity<?> getCompanyLnbList(@PathVariable("gnb-id") Long parId, @RequestParam(required = false) Boolean enabledYn) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getCompanyLnbList(parId, enabledYn)));
  }

  @GetMapping("/companies/auth/{auth-id}/gnb")
  public ResponseEntity<?> getGnbListOfAuth(@PathVariable("auth-id") Long authId) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getGnbListOfAuth(authId)));
  }
  @GetMapping("/companies/auth/{auth-id}/gnb/{par-id}")
  public ResponseEntity<?> getLnbListOfAuth(@PathVariable("auth-id") Long authId, @PathVariable("par-id") Long parMenuId) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getLnbListOfAuth(authId, parMenuId)));
  }
  @GetMapping("/companies/auth/{auth-id}/gnb-all")
  public ResponseEntity<?> getGnbListOfAuthWithAll(@PathVariable("auth-id") Long authId) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getGnbListOfAuthWithAll(authId)));
  }

  @GetMapping("/companies/auth/{auth-id}/gnb-all/{par-id}")
  public ResponseEntity<?> getGnbListOfAuthWithAll(@PathVariable("auth-id") Long authId, @PathVariable("par-id") Long parId) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getLnbListOfAuthWithAll(authId, parId)));
  }

  @GetMapping("/auth/{auth-id}")
  public ResponseEntity<?> getEmpListOfAuth(@PathVariable("auth-id") Long authId) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getEmpListOfAuth(authId)));
  }

  @PostMapping("/auth")
  public ResponseEntity<?> addAuth(@RequestBody AddAuthDto addAuthDto) {
    Long newAuthId = authGroupService.addAuth(addAuthDto);
    AuthResponseDto responseDto = new AuthResponseDto(newAuthId);
    return ResponseEntity.accepted().body(new SingleResponseDto<>(responseDto));
  }

  @PostMapping("/auth/{auth-id}/menu-mappings")
  public ResponseEntity<Void> modifyMappedMenuOfAuth(@PathVariable("auth-id") Long authId, @RequestBody Map<Long, Boolean> checkedMenuItems) {
    authGroupService.modifyMappedMenuOfAuth(authId, checkedMenuItems);
    return ResponseEntity.accepted().build();
  }

  @DeleteMapping("/auth/{auth-id}")
  public ResponseEntity<Void> deleteAuth(@PathVariable("auth-id")Long authId) {
    authGroupService.deactivateAuthByAuthId(authId);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/emp/{employee-id}/count")
  public ResponseEntity<?> getEmployeeAuthCount( @PathVariable("employee-id")Long employeeId) {
    return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.getEmployeeAuthCount(employeeId)));
  }

  @GetMapping("/employees/auth/list")
  public ResponseEntity<?> findEmployeeAuthList(
      @RequestParam(required = true) Long employeeId,
      @RequestParam(required = false) Long lastId,
      @RequestParam(required = false) String lastAuthName,
      @RequestParam(required = true) int pageSize,
      @RequestParam(required = false) String searchTerm,
      @RequestParam(required = false) String orderBy) throws UnsupportedEncodingException {
    String decodeSearchTerm = searchTerm==null? null : URLDecoder.decode(searchTerm,"UTF-8");
    if (lastAuthName != null) {
      String decodeLastAuthName = URLDecoder.decode(lastAuthName, "UTF-8");
      return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.findEmployeeAuthListOrderByAuthName(decodeLastAuthName, orderBy, decodeSearchTerm, employeeId, pageSize)));
    }
    if (lastId != null) {
      return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.findEmployeeAuthListOrderById(lastId, orderBy, decodeSearchTerm, employeeId,pageSize)));
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/employees/auth/list/edit")
  public ResponseEntity<?> findEmployeeAuthStatusList(
      @RequestParam(required = true) Long employeeId,
      @RequestParam(required = false) Long lastId,
      @RequestParam(required = false) String lastAuthName,
      @RequestParam(required = true) int pageSize,
      @RequestParam(required = false) String searchTerm,
      @RequestParam(required = false) String orderBy) throws UnsupportedEncodingException {
    String decodeSearchTerm = searchTerm==null? null : URLDecoder.decode(searchTerm,"UTF-8");
    if (lastAuthName != null) {
      String decodeLastAuthName = URLDecoder.decode(lastAuthName, "UTF-8");
      return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.findEmployeeAuthStatusListOrderByAuthName(decodeLastAuthName, orderBy, decodeSearchTerm, employeeId, pageSize)));
    }
    if (lastId != null) {
      return ResponseEntity.ok(new SingleResponseDto<>(authGroupService.findEmployeeAuthStatusListOrderById(lastId, orderBy, decodeSearchTerm, employeeId,pageSize)));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/employee/auth")
  public ResponseEntity<Void> addAuthEmployee(@RequestBody AddEmpAuthDto addEmpAuthDto) {
    authGroupService.addAuthEmployee(addEmpAuthDto);
    return ResponseEntity.ok().build();
  }

}