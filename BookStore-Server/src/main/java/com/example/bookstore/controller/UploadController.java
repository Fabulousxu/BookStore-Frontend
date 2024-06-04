package com.example.bookstore.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.util.Util;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class UploadController {
  @PostMapping("/image")
  public JSONObject uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
    String dirName = new File("src/main/resources/static/images").getCanonicalPath() + "/";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    StringBuilder fileName = new StringBuilder(LocalDateTime.now().format(formatter));
    String originalFileName = file.getOriginalFilename();
    String fileType = originalFileName.substring(originalFileName.lastIndexOf('.'));
    File uploadedFile = new File(dirName + fileName + fileType);
    while (uploadedFile.exists()) {
      fileName.append('_');
      uploadedFile = new File(dirName + fileName + fileType);
    }
    file.transferTo(uploadedFile);
    JSONObject res = Util.successResponseJson("上传成功"), data = new JSONObject();
    data.put("url", "http://127.0.0.1:8080/images/" + uploadedFile.getName());
    res.put("data", data);
    return res;
  }
}
