
package com.lms.controller;

import com.lms.service.FileUploadService;
import com.lms.service.LoginService;
import com.lms.repository.FileStaticRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

  private final FileUploadService fileUploadService;
  private final LoginService loginService;
  private final FileStaticRefRepository fileStaticRefRepository;

  @Autowired
  public FileUploadController(FileUploadService fileUploadService, LoginService loginService, FileStaticRefRepository fileStaticRefRepository) {
    this.fileUploadService = fileUploadService;
    this.loginService = loginService;
    this.fileStaticRefRepository = fileStaticRefRepository;
  }
  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Fetch the customer using the username
    Long customerId = loginService.findCustomerIdByUsername(username)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find customer information."));

    String fileName = file.getOriginalFilename();
    String fileExtension = fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";

    // Fetch FILE_ID from LMS_FILE_STATIC_REF based on FILE_EXTENSION using JPA
    Long fileId = fileStaticRefRepository.findByFileExtension(fileExtension)
      .map(fileStaticRef -> fileStaticRef.getFileId())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File extension not supported."));

    try {
      fileUploadService.storeFile(customerId, file);
      return ResponseEntity.ok("File uploaded successfully");
    } catch (IOException e) {
      // Log the exception
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store the file.");
    }
  }

}
