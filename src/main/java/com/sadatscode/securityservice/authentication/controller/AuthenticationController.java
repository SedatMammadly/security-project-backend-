package com.sadatscode.securityservice.authentication.controller;

import com.sadatscode.securityservice.authentication.model.AuthRequest;
import com.sadatscode.securityservice.authentication.model.AuthResponse;
import com.sadatscode.securityservice.authentication.model.RegisterRequest;
import com.sadatscode.securityservice.authentication.service.AuthenticationServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request){
        return  ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/refresh")
    public void refreshToken (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        service.refreshToken(request,response);
    }
}
