package adrianliz.testawss3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.*;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3ServiceImpl implements S3Service {
  private AmazonS3 amazonS3;
  private String bucketName;

  public S3ServiceImpl(AmazonS3 amazonS3, @Value("${aws.s3.bucket_name}") String bucketName) {
    this.amazonS3 = amazonS3;
    this.bucketName = bucketName;
  }

  @Override
  public String uploadFile(MultipartFile multipartFile) {
    File file = new File(multipartFile.getOriginalFilename());
    URL fileUrl = null;

    try (OutputStream stream = new FileOutputStream(file)) {
      stream.write(multipartFile.getBytes());
      String fileName = file.getName() + "_" + System.currentTimeMillis();

      PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
      amazonS3.putObject(request);
      fileUrl = amazonS3.getUrl(bucketName, fileName);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return fileUrl != null ? fileUrl.toString() : null;
  }

  @Override
  public InputStream downloadFile(String id) {
    S3Object file = null;

    try {
      file = amazonS3.getObject(bucketName, id);
    } catch (AmazonS3Exception ex) {
      ex.printStackTrace();
    }

    return file != null ? file.getObjectContent() : null;
  }
}
