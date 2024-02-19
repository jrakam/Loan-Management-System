package com.lms.repository;

import com.lms.entity.LmsCustomerAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LmsCustomerAuditRepository extends JpaRepository<LmsCustomerAudit, Long> {
}
