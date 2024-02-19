package com.lms.repository;


import com.lms.entity.FileStaticRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileStaticRefRepository extends JpaRepository<FileStaticRef, Long> {
  Optional<FileStaticRef> findByFileExtension(String fileExtension);
}
