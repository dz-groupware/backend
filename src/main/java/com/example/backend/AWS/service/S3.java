package com.example.backend.AWS.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class S3 {

  private final AmazonS3 amazonS3;
  @Value("dz-test-image")
  private String bucket;

  public String upload(MultipartFile multipartFile) throws IOException {
    String s3FileName = multipartFile.getOriginalFilename();
    System.out.println("file name : " + s3FileName);

    ObjectMetadata objMeta = new ObjectMetadata();
    objMeta.setContentLength(multipartFile.getInputStream().available());

    amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
    System.out.println("url : " + amazonS3.getUrl(bucket, s3FileName).toString());

    return amazonS3.getUrl(bucket, s3FileName).toString();
  }
}
