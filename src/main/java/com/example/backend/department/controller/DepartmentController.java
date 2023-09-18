package com.example.backend.department.controller;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.config.jwt.SecurityUtil;
import com.example.backend.department.dto.DeptDto;
import com.example.backend.department.dto.DeptListDto;
import com.example.backend.department.dto.EmpListDto;
import com.example.backend.department.service.DepartmentService;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    Long compId = SecurityUtil.getCompanyId();
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getDepartmentBasicList(compId)), HttpStatus.OK);
  }

  @PostMapping("/dept")
  public ResponseEntity<?> addDepartment(@RequestBody DeptDto dept) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.addDepartment(dept)), HttpStatus.OK);
  }

  @GetMapping("dept-list")
  public ResponseEntity<?> getDepartmentList(@RequestParam Long parId) {
    Long compId = SecurityUtil.getCompanyId();
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getDepartmentById(compId, parId)), HttpStatus.OK);
  }

  @GetMapping("detail-basic")
  public ResponseEntity<?> getBasicDetailById(@RequestParam Long id) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getBasicDetailById(id)), HttpStatus.OK);
  }

  @GetMapping("detail-emp")
  public ResponseEntity<?> getEmpListByDeptId(@RequestParam Long id){
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.getEmpListByDeptId(id)), HttpStatus.OK);
  }

  @PostMapping("/dept-modify")
  public ResponseEntity<?> modifyDepartment(@RequestBody DeptDto dept) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.modifyDepartment(dept)), HttpStatus.OK);
  }

  @PostMapping("/dept-all")
  public ResponseEntity<?> modifyAllDepartment(@RequestBody List<DeptDto> dept) {


    System.out.println(dept);
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.modifyAllDepartment(dept)), HttpStatus.OK);

  }

  @DeleteMapping("dept")
  public ResponseEntity<?> deleteDepartment(@RequestParam Long id) {
    return new ResponseEntity(
        new SingleResponseDto<>(departmentService.deleteDepartment(id)), HttpStatus.OK);
  }

  @GetMapping("/option-comp")
  public ResponseEntity<?>  getOptionCompList(){
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.getOptionCompList()), HttpStatus.OK);
  }

  @GetMapping("/dept")
  public ResponseEntity<?> findDeptNameAndCode(@RequestParam Long compId, @RequestParam String text){
    return new ResponseEntity<>(
        new SingleResponseDto<>(departmentService.findDeptNameAndCode(compId, text)), HttpStatus.OK);
  }
}