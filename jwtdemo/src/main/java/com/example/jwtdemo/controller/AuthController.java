package com.example.jwtdemo.controller;

import com.example.jwtdemo.dto.*;
import com.example.jwtdemo.service.AuthService;
import com.example.jwtdemo.service.BalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {

        System.out.println("Inside Controller");

        return authService.login(request);

    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(request);

    }

    /**
     * Logs out the authenticated user by invalidating
     * the Refresh Token stored in Redis.
     *
     * @param request Logout request containing Refresh Token.
     * @return Success message.
     */
    @PostMapping("/logout")
    public String logout(
            @RequestBody LogoutRequest request) {

        return authService.logout(request);

    }
}