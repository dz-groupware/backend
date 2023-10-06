package com.example.backend.department.controller;

import com.example.backend.common.dto.SingleResponseDto;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.service.DepartmentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/department")
public class DepartmentController {

  private final DepartmentService departmentService;

  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping("")
  public ResponseEntity<?> getDepartment() {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.getDepartmentBasicList()), HttpStatus.OK);
  }

  @GetMapping("dept-list")
  public ResponseEntity<?> getDepartmentList(@RequestParam Long parId) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.getDepartmentById(parId)), HttpStatus.OK);
  }

  @GetMapping("detail-basic")
  public ResponseEntity<?> getBasicDetailById(@RequestParam Long id) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.getBasicDetailById(id)), HttpStatus.OK);
  }

  @GetMapping("detail-emp")
  public ResponseEntity<?> getEmpListByDeptId(@RequestParam Long id){
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.getEmpListByDeptId(id)), HttpStatus.OK);
  }

  @PostMapping("/dept")
  public ResponseEntity<?> addDepartment(@RequestBody DeptDto dept) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.addDepartment(dept)), HttpStatus.OK);
  }

  @PostMapping("/dept-modify")
  public ResponseEntity<?> modifyDepartment(@RequestBody DeptDto dept) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.modifyDepartment(dept)), HttpStatus.OK);
  }

  @DeleteMapping("dept")
  public ResponseEntity<?> deleteDepartment(@RequestParam Long id) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.deleteDepartment(id)), HttpStatus.OK);
  }

  @GetMapping("/option-comp")
  public ResponseEntity<?>  getOptionCompList() {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.getOptionCompList()), HttpStatus.OK);
  }

  @GetMapping("/dept")
  public ResponseEntity<?> findDeptNameAndCode(@RequestParam Long compId, @RequestParam String text){
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.findDeptNameAndCode(compId, text)), HttpStatus.OK);
  }

  @GetMapping("/duplicate-test")
  public ResponseEntity<?> checkDeptCode(@RequestParam Long id, @RequestParam String text){
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.checkDeptCode(text, id)), HttpStatus.OK);
  }
  @PostMapping("/dept-all")
  public ResponseEntity<?> modifyAllDepartment(@RequestBody List<DeptDto> dept) {
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.modifyAllDepartment(dept)), HttpStatus.OK);
  }
}