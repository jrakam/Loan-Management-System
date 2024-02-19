package com.lms.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"LMS_CUSTOMER\"") // Use uppercase to match the database table name
public class LmsCustomer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"CUSTOMER_ID\"") // Use uppercase to match the database column name
  private Long customerId;

  @Column(name = "\"FIRST_NAME\"") // Use uppercase to match the database column name
  private String firstName;

  @Column(name = "\"LAST_NAME\"") // Use uppercase to match the database column name
  private String lastName;

  @Column(name = "\"USER_NAME\"") // Use uppercase to match the database column name
  private String userName;

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

// Getters and Setters
}
