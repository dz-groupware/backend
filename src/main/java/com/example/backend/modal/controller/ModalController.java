package com.example.backend.modal.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.service.ModalService;
import com.example.backend.config.jwt.PkDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
  public ResponseEntity<?> getAllProfile() {
    return new ResponseEntity<>(
        new SingleResponseDto<List<ProfileRes>>(modalService.getAllProfile()),
        HttpStatus.OK);
  }

  @GetMapping("/org/tree")
  public ResponseEntity<?> getOrgTree(@RequestParam String type, @RequestParam(required = false) Long compId, @RequestParam(required = false) Long deptId) {
    return new ResponseEntity<>(new SingleResponseDto<List<TreeItemRes>>(
        modalService.getOrgTree(type, deptId)), HttpStatus.OK);
  }

  @GetMapping("/org/empList")
  public ResponseEntity<?> findEmpList(String type, Long compId, Long deptId) {
    return new ResponseEntity<>(
        new SingleResponseDto<List<ProfileRes>>(modalService.findEmpList(type, compId, deptId)),
        HttpStatus.OK);
  }

  @GetMapping("/org/search")
  public ResponseEntity<?> findOrgSearchResult(@RequestParam String type, @RequestParam String text) {
    return new ResponseEntity<>(modalService.findOrgResult(type, text), HttpStatus.OK);

  }
}
