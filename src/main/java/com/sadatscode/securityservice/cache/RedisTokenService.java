package com.sadatscode.securityservice.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public String REFRESH_TOKEN_KEY_PREFIX = "refresh_token:";

    public void storeToken(String token, String email) {
        String redisKey = REFRESH_TOKEN_KEY_PREFIX + email;
        String refreshToken = redisTemplate.opsForValue().get(redisKey);
        if (refreshToken == null) {
            redisTemplate.opsForValue().set(redisKey, token, refreshTokenExpiration, TimeUnit.MILLISECONDS);
        }
    }
    public String getToken(String email) {
        String redisKey = REFRESH_TOKEN_KEY_PREFIX + email;
        return redisTemplate.opsForValue().get(redisKey);
    }
}
