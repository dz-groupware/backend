package com.example.backend.modal.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.service.ModalService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity getProfileByUserId() {
    return new ResponseEntity(
        new SingleResponseDto<List<ProfileRes>>(modalService.getProfileByUserId()),
        HttpStatus.OK);
  }

  @GetMapping("/org/tree")
  public ResponseEntity getOrgTree(@RequestParam String type) {
    return new ResponseEntity(new SingleResponseDto<List<TreeItemRes>>(
        modalService.getOrgTree(type)), HttpStatus.OK);
  }

  @GetMapping("/org/empList")
  public ResponseEntity findEmpList(@RequestParam String type) {
    return new ResponseEntity(
        new SingleResponseDto<List<ProfileRes>>(modalService.findEmpList(type)),
        HttpStatus.OK);
  }

  @GetMapping("/org/search")
  public ResponseEntity findOrgSearchResult(@RequestParam String type, @RequestParam String text) {
    return new ResponseEntity(modalService.findOrgSearchResult(type, text), HttpStatus.OK);

  }
}
