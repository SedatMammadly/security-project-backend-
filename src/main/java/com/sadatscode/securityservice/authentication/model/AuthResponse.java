package com.sadatscode.securityservice.authentication.model;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
