package com.lms.service;

import com.lms.entity.LmsCustomer;
import com.lms.entity.LmsCustomerAudit;
import com.lms.repository.LmsCustomerAuditRepository;
import com.lms.repository.LmsCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
  @Autowired
  private LmsCustomerRepository customerRepository;
  @Autowired
  private LmsCustomerAuditRepository auditRepository;
  @Autowired
  private EmailService emailService; // Ensure EmailService is not using static methods


  /**
   * Records a login attempt for a user by username.
   *
   * @param userName the username of the customer.
   */
  @Transactional
  public void recordLogin(String userName) {

    Optional<LmsCustomer> customerOptional = customerRepository.findByUserName(userName);
    customerOptional.ifPresent(customer -> {
      LmsCustomerAudit audit = new LmsCustomerAudit();
      audit.setCustomerId(customer.getCustomerId());
      audit.setLastLoginTime(LocalDateTime.now());
      auditRepository.save(audit);

      // Assume you have a method in LmsCustomer to get email, or it's stored somewhere else
      String customerEmail = ("rakamjyotshnapriya@gmail.com"); // Placeholder, replace with actual way to get email
      String emailSubject = "Login Alert";
      String emailBody = "Dear customer, Someone just logged into your Loan Management Account. If it was not you, please change your password immediately.";

      emailService.sendEmail(customerEmail, emailSubject, emailBody);
    });
  }

  /**
   * Finds a customer ID by their username.
   *
   * @param username the username to search for.
   * @return an Optional containing the customer ID if found.
   */
  public Optional<Long> findCustomerIdByUsername(String username) {
    return customerRepository.findByUserName(username)
      .map(LmsCustomer::getCustomerId); // Correctly accessing the customer ID if present
  }
}
