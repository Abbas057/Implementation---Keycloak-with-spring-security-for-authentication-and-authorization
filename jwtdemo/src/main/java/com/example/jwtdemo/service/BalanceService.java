package com.example.jwtdemo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {


    @PreAuthorize("hasRole('CUSTOMER')") // @PostAuthorize("returnObject.username == authentication.name") execure after method execution
    public String getBalance(){

        return  "Balance = ₹1,25,000";

    }

}
