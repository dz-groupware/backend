package com.example.backend.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.backend.config.jwt.SecurityUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
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

    String s3FileName = SecurityUtil.getCompanyId()+"/"+multipartFile.getOriginalFilename();

    ObjectMetadata objMeta = new ObjectMetadata();
    objMeta.setContentType("image/png");
    objMeta.setContentLength(multipartFile.getSize());

    amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
    return amazonS3.getUrl(bucket, s3FileName).toString();
  }

  public void createNewPrefix(Long compId) {
    // 파일 리스트 가져오기
    List<S3ObjectSummary> imageList = getImageList("icon");
    List<String> defaultIconList = imageList.stream()
        .map(S3ObjectSummary::getKey) // key 값을 추출
        .map(key -> key.replaceFirst("icon/", "")) // "Icon/" 부분을 잘라냄
        .collect(Collectors.toList()); // 리스트로 변환

    for (String icon : defaultIconList) {
      CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, "icon/"+icon, bucket, compId.toString()+"/"+icon);
      ObjectMetadata objMeta = new ObjectMetadata();
      objMeta.setContentType("image/png");
      copyObjectRequest.setNewObjectMetadata(objMeta);
      amazonS3.copyObject(copyObjectRequest);
    }
  }

  public String profileUploadFile(MultipartFile multipartFile) throws IOException {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); // 예: 20230925091857
    String timestamp = now.format(formatter);

    String originalFileName = multipartFile.getOriginalFilename();
    String s3FileName = "profile/" + timestamp + "_" + originalFileName;  // 타임스탬프와 원래의 파일명을 결합

    ObjectMetadata objMeta = new ObjectMetadata();
    objMeta.setContentType("image/png");
    objMeta.setContentLength(multipartFile.getSize());

    amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
    return amazonS3.getUrl(bucket, s3FileName).toString();
  }

  public List<S3ObjectSummary> getImageList(String prefix){
    return amazonS3.listObjects(bucket, prefix+"/").getObjectSummaries();
  }
}
