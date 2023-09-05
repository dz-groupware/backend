package com.example.backend.modal.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.PositionRes;
import com.example.backend.modal.dto.TreeItemRes;
import com.example.backend.modal.service.ModalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modal")
public class ModalController {
    private final ModalService modalService;

    public ModalController(ModalService modalService) {
        this.modalService = modalService;
    }

    @GetMapping("/profile")
    public ResponseEntity findProfileByEmpId(@RequestParam("empId") Long empId) {
        return new ResponseEntity(new SingleResponseDto<List<PositionRes>>(modalService.findProfileByEmpId(empId)), HttpStatus.OK);
    }
    @GetMapping("/org/tree")
    public ResponseEntity findOrgTree(@RequestParam String type, @RequestParam(required = false) Long empId, @RequestParam(required = false) Long compId, @RequestParam(required = false) Long deptId) {
        return new ResponseEntity(new SingleResponseDto<List<TreeItemRes>>(modalService.findOrgTree(type, empId, compId, deptId)), HttpStatus.OK);
    }

    @GetMapping("/org/empList")
    public ResponseEntity findEmpList(@RequestParam String type, @RequestParam(required = false) Long compId, @RequestParam(required = false) Long deptId) {
        return new ResponseEntity(new SingleResponseDto<List<ProfileRes>>(modalService.findEmpList(type, compId, deptId)), HttpStatus.OK);
    }
    @GetMapping("/org/search")
    public ResponseEntity findOrgSearchResult(@RequestParam String type, @RequestParam String text){
        return new ResponseEntity(modalService.findOrgSearchResult(type, text), HttpStatus.OK);

    }
}
