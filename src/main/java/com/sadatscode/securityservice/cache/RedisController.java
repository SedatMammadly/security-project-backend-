package com.sadatscode.securityservice.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RedisController {
    private final RedisTokenService redisTokenService;

    @GetMapping("/get")
   public ResponseEntity<String> getTokenFromRedis(@RequestParam String email) {
        return ResponseEntity.ok(redisTokenService.getToken(email));
    }
}
