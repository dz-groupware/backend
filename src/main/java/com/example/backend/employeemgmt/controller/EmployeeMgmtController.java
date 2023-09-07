//package com.example.backend.employeemgmt.controller;
//
//import com.example.backend.common.SingleResponseDto;
//import com.example.backend.employee.service.EmployeeService;
//import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
//import com.example.backend.employeemgmt.service.EmployeeMgmtService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeMgmtController {
//    private final EmployeeMgmtService employeeMgmtService;
//    public EmployeeMgmtController(EmployeeMgmtService employeeMgmtService) {
//        this.employeeMgmtService = employeeMgmtService;
//    }
//    @GetMapping
//    public ResponseEntity getEmployeeMgmtList(){
//        return new ResponseEntity<>(new SingleResponseDto<>(employeeMgmtService.getEmployeeMgmtList()),
//                HttpStatus.OK);
//    }
//
//
//    @PostMapping
//    public ResponseEntity addEmployeeMgmt(@RequestBody EmployeeMgmtReqDto employee) {
//        employeeMgmtService.addEmployeeMgmt(employee);
//        return new ResponseEntity<>(new SingleResponseDto("성공"),
//                HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity getEmployeeDetailsById(@PathVariable Long id) {
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(employeeMgmtService.getEmployeeDetailsById(id)),
//                HttpStatus.OK);
//    }
//
//
//    @GetMapping("/company-list")
//    public ResponseEntity findEmployeeMgmtList(@RequestParam String compId, String searchType) {
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(employeeMgmtService.findEmployeeMgmtList(compId, searchType)),
//                HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity modifyEmployeeMgmt(@PathVariable Long id, @RequestBody EmployeeMgmtReqDto company) {
//        employeeMgmtService.modifyEmployeeMgmt(id, company);
//        return new ResponseEntity<>(new SingleResponseDto("성공"),
//                HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity removeEmployeeMgmt(@PathVariable Long id) {
//        employeeMgmtService.removeEmployeeMgmt(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//}
//
