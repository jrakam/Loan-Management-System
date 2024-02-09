package com.lms.listener;

import com.lms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {

  private final CustomerService customerService;

  @Autowired
  public AuthenticationSuccessListener(CustomerService customerService) {
    this.customerService = customerService;
  }

  @EventListener
  public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
    String ldapUsername = event.getAuthentication().getName();
    customerService.findCustomerIdByUsername(ldapUsername).ifPresent(customerId -> {
      customerService.recordLoginTime(customerId);
    });
  }
}
