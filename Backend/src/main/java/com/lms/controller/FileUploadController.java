package com.lms.controller;
//
//import com.lms.service.CustomerService;
//import com.lms.service.FileUploadService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//
//
//@RestController
//@RequestMapping("/api/files")
//public class FileUploadController {
//
//  private final FileUploadService fileStorageService;
//  private final CustomerService customerService;
//
//  @Autowired
//  public FileUploadController(FileUploadService fileStorageService, CustomerService customerService) {
//    this.fileStorageService = fileStorageService;
//    this.customerService = customerService;
//  }
//
//  @PostMapping("/upload")
//  public String uploadFile(@RequestParam("file") MultipartFile file) {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String ldapUsername = authentication.getName();
//
//    // Use the CustomerService to find the customerId by ldapUsername
//    customerService.findCustomerIdByUsername(ldapUsername).ifPresent(customerId -> {
//      String fileName = file.getOriginalFilename();
//      String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//      // Assuming your fileStorageService can handle storing file details with customerId
//      fileStorageService.storeFile(customerId, fileName, fileExtension);
//    });
//
//    return "File uploaded successfully";
//  }
//}

import com.lms.service.CustomerService;
import com.lms.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

  private final FileUploadService fileStorageService;
  private final CustomerService customerService;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public FileUploadController(FileUploadService fileStorageService, CustomerService customerService, DataSource dataSource) {
    this.fileStorageService = fileStorageService;
    this.customerService = customerService;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String ldapUsername = authentication.getName();

    // Use the CustomerService to find the customerId by ldapUsername
    customerService.findCustomerIdByUsername(ldapUsername).ifPresent(customerId -> {
      String fileName = file.getOriginalFilename();
      String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

      // Fetch FILE_ID from LMS_FILE_STATIC_REF based on FILE_EXTENSION using JdbcTemplate
      Long fileId = getFileIdByFileExtension(fileExtension);

      // Assuming your fileStorageService can handle storing file details with customerId and fileId
      fileStorageService.storeFile(customerId,fileId, fileName, fileExtension);
    });

    return "File uploaded successfully";
  }

  // Method to fetch FILE_ID from LMS_FILE_STATIC_REF based on FILE_EXTENSION
  private Long getFileIdByFileExtension(String fileExtension) {
    String sql = "SELECT \"FILE_ID\" FROM \"LMS_FILE_STATIC_REF\" WHERE \"FILE_EXTENSION\" = ?";
    return jdbcTemplate.queryForObject(sql, Long.class, fileExtension);
  }
}
