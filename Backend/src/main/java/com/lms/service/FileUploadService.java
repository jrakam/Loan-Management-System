package com.lms.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//  import org.springframework.jdbc.core.JdbcTemplate;
//  import org.springframework.stereotype.Service;
//  import java.time.LocalDateTime;
//
//@Service
//public class FileUploadService {
//
//  @Autowired
//  private JdbcTemplate jdbcTemplate;
//
//  public void storeFile(Long customerId, String fileName, String fileExtension) {
//    String sql = "INSERT INTO \"LMS_CUSTOMER_FILE_UPLOAD\" (\"CUSTOMER_ID\", \"FILE_NAME\", \"FILE_EXTENSION\", \"FILE_UPLOAD_TIME\") VALUES (?, ?, ?, ?)";
//    jdbcTemplate.update(sql, customerId, fileName, fileExtension, LocalDateTime.now());
//  }
//}



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FileUploadService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void storeFile(Long customerId, Long fileId, String fileName, String fileExtension) {
    String sql = "INSERT INTO \"LMS_CUSTOMER_FILE_UPLOAD\" (\"CUSTOMER_ID\", \"FILE_NAME\", \"FILE_EXTENSION\", \"FILE_UPLOAD_TIME\") VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, customerId, fileName, fileId, LocalDateTime.now());
  }
}
