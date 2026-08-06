package com.example.jwtdemo.service;

import com.example.jwtdemo.dto.*;
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

    private final RefreshTokenService refreshTokenService;

    public TokenResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails user =
                userDetailsService.loadUserByUsername(
                        request.getUsername()
                );

        String accessToken =
                jwtService.generateToken(user);

        String refreshToken =
                jwtService.generateRefreshToken(user);

        refreshTokenService.saveRefreshToken(
                user.getUsername(),
                refreshToken
        );

        return new TokenResponse(
                accessToken,
                refreshToken
        );
    }

    public TokenResponse refreshToken(
            RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUsername(refreshToken);

        String storedRefreshToken =
                refreshTokenService.getRefreshToken(username);

        if (storedRefreshToken == null ||
                !storedRefreshToken.equals(refreshToken)) {

            throw new RuntimeException("Invalid Refresh Token");

        }

        UserDetails user =
                userDetailsService.loadUserByUsername(username);

        String newAccessToken =
                jwtService.generateToken(user);

        String newRefreshToken =
                jwtService.generateRefreshToken(user);

        refreshTokenService.saveRefreshToken(
                username,
                newRefreshToken
        );

        return new TokenResponse(
                newAccessToken,
                newRefreshToken
        );
    }

    /**
     * Invalidates the Refresh Token by removing it
     * from Redis.
     *
     * @param request Logout request.
     * @return Logout status message.
     */
    public String logout(
            LogoutRequest request) {

        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUsername(refreshToken);

        refreshTokenService.deleteRefreshToken(username);

        return "Logout Successful";

    }

}