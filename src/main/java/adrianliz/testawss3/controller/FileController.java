package adrianliz.testawss3.controller;

import adrianliz.testawss3.service.S3Service;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public final class FileController {
  private S3Service s3Service;

  public FileController(S3Service s3Service) {
    this.s3Service = s3Service;
  }

  @PostMapping("/upload")
  public ResponseEntity<HashMap<String, Serializable>> uploadFile(
      @RequestPart("file") MultipartFile file) {

    return ResponseEntity.ok(
        new HashMap<>() {
          {
            put("url", s3Service.uploadFile(file));
          }
        });
  }

  @GetMapping("/download/{id}")
  public ResponseEntity downloadFile(@PathVariable("id") String id) {
    InputStream streamResource = s3Service.downloadFile(id);
    if (streamResource == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + "\"")
        .body(new InputStreamResource(streamResource));
  }
}
