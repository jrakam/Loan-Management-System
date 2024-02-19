package com.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "LMS_FILE_STATIC_REF")
public class FileStaticRef {

  @Id
  @Column(name = "FILE_REF_ID")
  private Long fileRefId;
  @Column(name = "FILE_ID")
  private Long fileId;
  @Column(name = "FILE_EXTENSION")
  private String fileExtension;

  // Getters and Setters


  public Long getFileRefId() {
    return fileRefId;
  }

  public void setFileRefId(Long fileRefId) {
    this.fileRefId = fileRefId;
  }

  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }
}
