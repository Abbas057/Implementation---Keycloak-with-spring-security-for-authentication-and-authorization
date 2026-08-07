package com.example.jwtdemo.controller;

import com.example.jwtdemo.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;


    @GetMapping

    public String getBalance() {

        return balanceService.getBalance();

    }
}