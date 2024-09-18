package com.sadatscode.securityservice.authentication.service;


import com.sadatscode.securityservice.authentication.model.AuthRequest;
import com.sadatscode.securityservice.authentication.model.AuthResponse;
import com.sadatscode.securityservice.authentication.model.RegisterRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
     AuthResponse register(RegisterRequest request);
     AuthResponse authenticate(AuthRequest request);
     void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
