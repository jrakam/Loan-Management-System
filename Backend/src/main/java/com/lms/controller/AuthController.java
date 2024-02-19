package com.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @GetMapping("/check-session")
  public boolean checkSession() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser");
    logger.info("Checking session: isAuthenticated = {}", isAuthenticated);
    return isAuthenticated;
  }

  @PostMapping("/logout-success")
  public String logout() {
    logger.info("Logout successful");
    return "You have been logged out successfully.";
  }
}
