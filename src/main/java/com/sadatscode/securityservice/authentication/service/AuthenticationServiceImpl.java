package com.sadatscode.securityservice.authentication.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadatscode.securityservice.authentication.jwt.JwtService;
import com.sadatscode.securityservice.authentication.model.AuthRequest;
import com.sadatscode.securityservice.authentication.model.AuthResponse;
import com.sadatscode.securityservice.authentication.model.RegisterRequest;
import com.sadatscode.securityservice.cache.RedisTokenService;
import com.sadatscode.securityservice.model.Role;
import com.sadatscode.securityservice.model.User;
import com.sadatscode.securityservice.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RedisTokenService redisTokenService;


    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
        .build();
        repository.save(user);
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        redisTokenService.storeToken(request.getEmail(),refreshToken);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("user not found"));
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        redisTokenService.storeToken(request.getEmail(),refreshToken);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        String userEmail;
        String refreshToken;
        AuthResponse authResponse;
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ;
        }
         refreshToken=authHeader.substring(7);
         userEmail = jwtService.extractUsername(refreshToken);
         if (userEmail != null) {
            var user = this.userDetailsService.loadUserByUsername(userEmail);
             if(jwtService.validateToken(user, refreshToken)) {
                 String accessToken = jwtService.generateAccessToken(userEmail);
                  authResponse = AuthResponse.builder()
                         .refreshToken(refreshToken)
                         .accessToken(accessToken).build();
                 new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
             }
         }

    }


}
