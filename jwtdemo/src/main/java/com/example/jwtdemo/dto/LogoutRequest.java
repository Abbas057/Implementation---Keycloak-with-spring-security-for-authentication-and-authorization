package com.example.jwtdemo.dto;

import lombok.Data;

/**
 * Request DTO used for user logout.
 * Carries the Refresh Token that should be invalidated.
 */
@Data
public class LogoutRequest {

    private String accessToken;

    private String refreshToken;

}