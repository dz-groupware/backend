//package com.example.backend.user;
//
//import com.example.backend.common.SingleResponseDto;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//  private final UserServiceImpl userService;
//
//  public UserController(UserServiceImpl userService) {
//    this.userService = userService;
//  }
//
//  @PostMapping("/re-login")
//  public ResponseEntity<?> getAnotherLogin(@RequestBody EmpIdRequestDto requestBody,  HttpServletResponse response) {
//    return new ResponseEntity<>(new SingleResponseDto<>(
//        userService.getAnotherLogin(response, requestBody.getEmpId())
//    ), HttpStatus.ACCEPTED);
//  }
//}
