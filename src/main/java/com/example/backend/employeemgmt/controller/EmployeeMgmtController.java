package com.example.backend.employeemgmt.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.service.EmployeeMgmtService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeemgmt")
public class EmployeeMgmtController {
    private final EmployeeMgmtService employeeMgmtService;
    public EmployeeMgmtController(EmployeeMgmtService employeeMgmtService) {
        this.employeeMgmtService = employeeMgmtService;
    }
    @GetMapping
    public ResponseEntity getEmployeeMgmtList(){
        return new ResponseEntity<>(new SingleResponseDto<>(employeeMgmtService.getEmployeeMgmtList()),
                HttpStatus.OK);
    }

    @GetMapping("/dep")
    public ResponseEntity getAllDepartmentMgmtList() {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.getAllDepartmentMgmtList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEmployeeDetailsById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.getEmployeeDetailsById(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addEmployeeMgmt(@RequestBody EmployeeMgmtReqDto employeeMgmt) {

        employeeMgmtService.addEmployeeMgmt(employeeMgmt);
        return new ResponseEntity<>(new SingleResponseDto("성공"),
                HttpStatus.CREATED);
    }
    @GetMapping("/employee-list")
    public ResponseEntity findEmployeeMgmtList(@RequestParam Long compId, String text) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.findEmployeeMgmtList(compId, text)),
                HttpStatus.OK);
    }


    @PutMapping
   public ResponseEntity modifyEmployeeMgmt(@RequestBody EmployeeMgmtReqDto employeeMgmt) {
        employeeMgmtService.modifyEmployeeMgmt(employeeMgmt);
        return new ResponseEntity<>(new SingleResponseDto("성공"),
                HttpStatus.OK);
    }

    @PutMapping("/del/{id}")
    public ResponseEntity removeEmployeeMgmt(@PathVariable Long id ,@RequestBody EmployeeMgmtReqDto employeeMgmt) {
        employeeMgmtService.removeEmployeeMgmt(id,employeeMgmt);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

