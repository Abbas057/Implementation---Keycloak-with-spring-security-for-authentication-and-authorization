package com.example.jwtdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service responsible for managing revoked JWT tokens.
 * Stores blacklisted token IDs in Redis with expiry.
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;


    /**
     * Adds JWT ID into blacklist.
     *
     * @param jti JWT unique identifier
     * @param ttl Remaining token validity time
     */
    public void blacklistToken(
            String jti,
            long ttl) {

        redisTemplate.opsForValue()
                .set(
                        "blacklist:" + jti,
                        "true",
                        ttl,
                        TimeUnit.MILLISECONDS
                );
    }


    /**
     * Checks whether token is revoked.
     *
     * @param jti JWT ID
     * @return true if token is blacklisted
     */
    public boolean isBlacklisted(String jti) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(
                        "blacklist:" + jti
                )
        );
    }
}