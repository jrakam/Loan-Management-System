package com.lms.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {

  private final JdbcTemplate jdbcTemplate;
  private static final Logger logger = LogManager.getLogger(CustomerService.class);

  @Autowired
  public CustomerService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Finds a customer's ID based on their LDAP username.
   *
   * @param username The LDAP username of the customer.
   * @return An Optional containing the customer ID if found, or empty otherwise.
   */
  public Optional<Long> findCustomerIdByUsername(String username) {
    // Use double quotes around identifiers to preserve case sensitivity
    String sql = "SELECT \"CUSTOMER_ID\" FROM \"LMS_CUSTOMER\" WHERE \"USER_NAME\" = ?";
    try {
      Long customerId = jdbcTemplate.queryForObject(sql, new Object[]{username}, Long.class);
      return Optional.ofNullable(customerId);
    } catch (Exception e) {
      logger.error("Error finding customer ID by username: {}", e.getMessage(), e);
      return Optional.empty();
    }
  }

  /**
   * Records the login time of a customer by their ID.
   *
   * @param customerId The ID of the customer whose login time is to be recorded.
   */
  public void recordLoginTime(Long customerId) {
    // Use double quotes around identifiers to preserve case sensitivity
    String sql = "INSERT INTO \"LMS_CUSTOMER_AUDIT\" (\"CUSTOMER_ID\", \"LAST_LOGIN_TIME\") VALUES (?, CURRENT_TIMESTAMP)";
    jdbcTemplate.update(sql, customerId);
  }

}

