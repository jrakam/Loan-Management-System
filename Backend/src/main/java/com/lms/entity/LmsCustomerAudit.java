package com.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "LMS_CUSTOMER_AUDIT") // Specify table name in uppercase
public class LmsCustomerAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CUSTOMER_AUDIT_ID") // Specify column name in uppercase
  private Long customerAuditId;

  @Column(name = "CUSTOMER_ID") // Specify column name in uppercase
  private Long customerId;

  @Column(name = "LAST_LOGIN_TIME") // Specify column name in uppercase
  private LocalDateTime lastLoginTime;

  // Getters and Setters

  public Long getCustomerAuditId() {
    return customerAuditId;
  }

  public void setCustomerAuditId(Long customerAuditId) {
    this.customerAuditId = customerAuditId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public LocalDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(LocalDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }
}
