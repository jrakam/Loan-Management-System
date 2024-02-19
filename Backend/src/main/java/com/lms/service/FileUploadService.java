package com.lms.service;

import com.lms.entity.FileStaticRef;
import com.lms.entity.LmsCustomerFileUpload;
import com.lms.repository.FileStaticRefRepository;
import com.lms.repository.LmsCustomerFileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileUploadService {

  @Autowired
  private LmsCustomerFileUploadRepository customerFileUploadRepository;

  @Autowired
  private FileStaticRefRepository fileStaticRefRepository;

  private final String uploadDir = "/Users/jyoshnapriyarakam/Loan_Management_System/Backend/dst"; // Adjust this path



  public void storeFile(Long customerId, MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      throw new IOException("Failed to store empty file.");
    }

    String fileName = file.getOriginalFilename();
    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

    Long fileId = fileStaticRefRepository.findByFileExtension(fileExtension)
      .map(FileStaticRef::getFileId)
      .orElseThrow(() -> new IllegalStateException("File extension not supported."));

    File targetFile = new File(uploadDir + File.separator + fileName);
    // Ensure directory exists
    targetFile.getParentFile().mkdirs();
    // Save file to filesystem
    file.transferTo(targetFile);

    // Save file metadata to database
    LmsCustomerFileUpload fileUpload = new LmsCustomerFileUpload();
    fileUpload.setCustomerId(customerId);
   // // Set the file_id obtained from lms_file_static_ref
    fileUpload.setFileName(fileName);
    fileUpload.setFileExtension(fileId); // You might want to adjust this
    fileUpload.setFileUploadTime(LocalDateTime.now());
    customerFileUploadRepository.save(fileUpload);
  }

}
