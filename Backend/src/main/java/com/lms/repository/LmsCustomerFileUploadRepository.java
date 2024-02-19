package com.lms.repository;


  import com.lms.entity.LmsCustomerFileUpload;
  import org.springframework.data.jpa.repository.JpaRepository;

public interface LmsCustomerFileUploadRepository extends JpaRepository<LmsCustomerFileUpload, Long> {
}
