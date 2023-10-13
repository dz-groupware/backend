package com.example.backend.employeemgmt.controller;

import com.example.backend.employeemgmt.dto.EmployeeMgmtCheckSignUpResultResDto;
import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtReqDto;
import com.example.backend.employeemgmt.dto.EmployeeMgmtSignUpReqDto;
import com.example.backend.employeemgmt.service.EmployeeMgmtService;
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

    @GetMapping("/incumbent")
    public ResponseEntity getIncumbentEmployeeMgmtList(){
        return new ResponseEntity<>(new SingleResponseDto<>(employeeMgmtService.getIncumbentEmployeeMgmtList()),
                HttpStatus.OK);
    }
    @GetMapping("/quitter")
    public ResponseEntity getQuitterEmployeeMgmtList(){
        return new ResponseEntity<>(new SingleResponseDto<>(employeeMgmtService.getQuitterEmployeeMgmtList()),
                HttpStatus.OK);
    }


    @GetMapping("/dep/{companyId}")
    public ResponseEntity getAllDepartmentMgmtList(@PathVariable Long companyId) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.getAllDepartmentMgmtList(companyId)),
                HttpStatus.OK);
    }

    @GetMapping("/departmentlist")
    public ResponseEntity getDepartmentList() {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.getDepartmentList()),
                HttpStatus.OK);
    }
    @GetMapping("/{companyId}/hasCEO")
    public ResponseEntity checkIfCompanyHasCEO(@PathVariable Long companyId) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.checkIfCompanyHasCEO(companyId)),
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
    public ResponseEntity findEmployeeMgmtList(@RequestParam Long deptId, String text) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.findEmployeeMgmtList(deptId, text)),
                HttpStatus.OK);
    }

    @GetMapping("/employee-list/open")
    public ResponseEntity findOpenEmployeeMgmtList(@RequestParam Long deptId, String text) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.findOpenEmployeeMgmtList(deptId, text)),
                HttpStatus.OK);
    }

    @GetMapping("/employee-list/close")
    public ResponseEntity findCloseEmployeeMgmtList(@RequestParam Long deptId, String text) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.findCloseEmployeeMgmtList(deptId, text)),
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

    @PostMapping("/idcheck/{loginId}")
    public ResponseEntity checkLoginId(@PathVariable String loginId) {
        return new ResponseEntity<>(
                new SingleResponseDto<>(employeeMgmtService.checkLoginId(loginId)),
                HttpStatus.OK);
    }
    @PostMapping("/signupcheck")
    public ResponseEntity checkSignUp(@RequestBody EmployeeMgmtSignUpReqDto employeeMgmt) {
        EmployeeMgmtCheckSignUpResultResDto result = employeeMgmtService.checkSignUp(employeeMgmt);

        if (result.isFromCheck() && (result.getData() == null || result.getData().isEmpty())) {
            // if 문에서 결과가 나왔지만 데이터가 없는 경우
            return new ResponseEntity<>("No data found from check", HttpStatus.NOT_FOUND);
        } else if (!result.isFromCheck()) {
            // if 문에 들어가지 않은 경우
            return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SingleResponseDto<>(result.getData()), HttpStatus.OK);
    }



}

