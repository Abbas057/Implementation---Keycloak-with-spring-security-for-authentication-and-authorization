package com.example.jwtdemo.service;

import com.example.jwtdemo.dto.LoginRequest;
import com.example.jwtdemo.dto.LoginResponse;
import com.example.jwtdemo.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        System.out.println("Inside AuthService");

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            System.out.println("Authentication Success");

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }

        UserDetails user =
                userDetailsService.loadUserByUsername(request.getUsername());

        System.out.println("Stored Hash : " + user.getPassword());

        System.out.println("Matches : " +
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                ));


        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}