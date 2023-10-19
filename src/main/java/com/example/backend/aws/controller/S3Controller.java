package com.example.backend.aws.controller;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.backend.aws.service.S3;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
@Slf4j
public class S3Controller {

  private final S3 s3;

  @GetMapping("/imgList")
  public ResponseEntity<List<S3ObjectSummary>> test() {
    return new ResponseEntity<>(s3.getImageList(), HttpStatus.OK);
  }

  @PostMapping("/img")
  public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile)
          throws IOException {
    log.info("multipartFile : " + multipartFile);
    return new ResponseEntity<String>(s3.upload(multipartFile), HttpStatus.OK);
  }

  @PostMapping("/profile")
  public ResponseEntity<?> profileUploadFile(@RequestParam("profileImage") MultipartFile multipartFile) {
    String uploadedUrl= null;
    try {
      uploadedUrl= s3.profileUploadFile(multipartFile);
    } catch (Exception e) {
      return new ResponseEntity<String>("Error while uploading: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>(uploadedUrl, HttpStatus.OK);
  }

  @GetMapping("/test")
  public ResponseEntity<?> test(@RequestParam("compId") Long compId) {
    s3.createNewPrefix(compId);
    return new ResponseEntity<Long>(compId, HttpStatus.OK);
  }


}
