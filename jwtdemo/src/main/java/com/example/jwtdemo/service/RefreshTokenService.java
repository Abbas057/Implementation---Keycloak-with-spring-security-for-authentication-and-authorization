package com.example.jwtdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final long REFRESH_TOKEN_EXPIRY_DAYS = 7;

    public void saveRefreshToken(String username,
                                 String refreshToken) {

        redisTemplate.opsForValue().set(
                username,
                refreshToken,
                Duration.ofDays(REFRESH_TOKEN_EXPIRY_DAYS)
        );
    }

    public String getRefreshToken(String username) {

        return redisTemplate.opsForValue().get(username);

    }

    public void deleteRefreshToken(String username) {

        redisTemplate.delete(username);

    }

}