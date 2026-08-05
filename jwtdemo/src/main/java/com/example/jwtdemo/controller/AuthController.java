package com.example.jwtdemo.controller;

import com.example.jwtdemo.dto.LoginRequest;
import com.example.jwtdemo.dto.LoginResponse;
import com.example.jwtdemo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        System.out.println("Inside Controller");

        return authService.login(request);

    }

    @RestController
    @RequestMapping("/balance")
    public class BalanceController {

        @GetMapping
        public String getBalance() {
            return "Balance = ₹1,25,000";
        }
    }
}