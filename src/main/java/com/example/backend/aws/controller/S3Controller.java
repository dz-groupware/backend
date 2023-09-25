package com.example.backend.aws.controller;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.backend.aws.service.S3;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class S3Controller {

  private final S3 s3;

  @GetMapping("/imgList")
  public ResponseEntity<List<S3ObjectSummary>> test() {
    return new ResponseEntity<>(s3.getImageList(), HttpStatus.OK);
  }

  @PostMapping("/img")
  public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile)
      throws IOException {
    System.out.println(multipartFile);
    return new ResponseEntity<String>(s3.upload(multipartFile), HttpStatus.OK);
  }
}
