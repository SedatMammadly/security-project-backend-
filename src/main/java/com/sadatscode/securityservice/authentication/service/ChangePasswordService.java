package com.sadatscode.securityservice.authentication.service;

import com.sadatscode.securityservice.authentication.model.ChangePasswordRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface ChangePasswordService {
    String changePassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest);
}
