package com.lms.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


@RestController
public class ApplicationController {

    @GetMapping("/api/login")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

}

