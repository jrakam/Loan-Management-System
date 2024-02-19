package com.lms.listener;
//
//import com.lms.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticationSuccessListener {
//
//  private final CustomerService customerService;
//
//  @Autowired
//  public AuthenticationSuccessListener(CustomerService customerService) {
//    this.customerService = customerService;
//  }
//
//  @EventListener
//  public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
//    String ldapUsername = event.getAuthentication().getName();
//    customerService.findCustomerIdByUsername(ldapUsername).ifPresent(customerId -> {
//      customerService.recordLoginTime(customerId);
//    });
//  }
//}

import com.lms.service.EmailService;
import com.lms.service.LoginService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginEventListener {
  @Autowired
  private LoginService loginService;

  @EventListener
  public void onLoginSuccess(InteractiveAuthenticationSuccessEvent event) {
    // Assuming the principal's username is the same as LmsCustomer's userName
    String userName = event.getAuthentication().getName();
    loginService.recordLogin(userName);


  }
}
