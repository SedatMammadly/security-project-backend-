package com.sadatscode.securityservice.authentication.service;

import com.sadatscode.securityservice.authentication.model.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService{
    private final PasswordEncoder passwordEncoder;

    @Override
    public String changePassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest) {
        return "";
    }


}
