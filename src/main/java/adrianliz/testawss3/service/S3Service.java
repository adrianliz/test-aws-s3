package adrianliz.testawss3.service;

import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
  String uploadFile(MultipartFile file);

  InputStream downloadFile(String id);
}
