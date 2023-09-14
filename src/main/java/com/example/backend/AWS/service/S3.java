package com.example.backend.AWS.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.IOException;
import java.util.List;
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
    String s3FileName = "icon/"+multipartFile.getOriginalFilename();

    ObjectMetadata objMeta = new ObjectMetadata();
    objMeta.setContentType("image/png");
    objMeta.setContentLength(multipartFile.getSize());

    amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
    return amazonS3.getUrl(bucket, s3FileName).toString();
  }

  public List<S3ObjectSummary> getImageList(){
    return amazonS3.listObjects(bucket, "icon/").getObjectSummaries();
  }
}
