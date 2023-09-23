package com.example.backend.modal.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.service.ModalService;
import com.example.backend.setting.dto.JwtDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modal")
public class ModalController {

  private final ModalService modalService;

  public ModalController(ModalService modalService) {
    this.modalService = modalService;
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getAllProfile(@CookieValue("JWT") String jwt) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<ProfileRes>>(modalService.getAllProfile(new JwtDto(jwt))),
        HttpStatus.OK);
  }

  @GetMapping("/org/tree")
  public ResponseEntity<?> getOrgTree(@CookieValue("JWT") String jwt, @RequestParam String type, @RequestParam(required = false) Long compId, @RequestParam(required = false) Long deptId) {
    return new ResponseEntity<>(new SingleResponseDto<List<TreeItemRes>>(
        modalService.getOrgTree(new JwtDto(jwt), type, deptId)), HttpStatus.OK);
  }

  @GetMapping("/org/empList")
  public ResponseEntity<?> findEmpList(@CookieValue("JWT") String jwt, String type, Long compId, Long deptId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<ProfileRes>>(modalService.findEmpList(type, compId, deptId)),
        HttpStatus.OK);
  }

  @GetMapping("/org/search")
  public ResponseEntity<?> findOrgSearchResult(@CookieValue("JWT") String jwt, @RequestParam String type, @RequestParam String text) {
    return new ResponseEntity<>(modalService.findOrgResult(new JwtDto(jwt), type, text), HttpStatus.OK);

  }
}
