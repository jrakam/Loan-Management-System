package com.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import  jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
@Table(name = "LMS_CUSTOMER_FILE_UPLOAD")
public class LmsCustomerFileUpload {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"CUSTOMER_FILE_UPLOAD_ID\"") // Use uppercase to match the database column name
  private Long customerFileUploadIdId;

  @Column(name = "CUSTOMER_ID")
  private Long customerId;

  @Column(name = "FILE_NAME")
  private String fileName;

  @Column(name = "FILE_EXTENSION")
  private Long fileExtension;

  @Column(name = "FILE_UPLOAD_TIME")
  private LocalDateTime fileUploadTime;

  public Long getCustomerFileUploadIdId() {

    return customerFileUploadIdId;
  }

  public void setCustomerFileUploadIdId(Long customerFileUploadIdId) {
    this.customerFileUploadIdId = customerFileUploadIdId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Long getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(Long fileExtension) {
    this.fileExtension = fileExtension;
  }

  public LocalDateTime getFileUploadTime() {
    return fileUploadTime;
  }

  public void setFileUploadTime(LocalDateTime fileUploadTime) {
    this.fileUploadTime = fileUploadTime;
  }
}
