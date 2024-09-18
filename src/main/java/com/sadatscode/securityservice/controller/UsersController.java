package com.sadatscode.securityservice.controller;

import com.sadatscode.securityservice.model.User;
import com.sadatscode.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/user/")
@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UserRepository repository;
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return  repository.findAll();
    }
}
