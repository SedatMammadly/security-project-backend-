package com.sadatscode.securityservice.authentication.controller;

import com.sadatscode.securityservice.authentication.model.ChangePasswordRequest;
import com.sadatscode.securityservice.authentication.service.ChangePasswordServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordController {
    private final ChangePasswordServiceImpl service;

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ChangePasswordRequest request ){
        return ResponseEntity.ok(service.changePassword(userDetails,request));
    }
}
