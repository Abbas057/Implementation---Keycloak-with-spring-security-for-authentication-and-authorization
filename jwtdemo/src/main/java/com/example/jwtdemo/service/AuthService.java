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

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final TokenBlacklistService tokenBlacklistService;

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
    /**
     * Logs out user by:
     * 1. Blacklisting Access Token
     * 2. Removing Refresh Token from Redis
     *
     * @param request logout request
     * @return logout message
     */
    public String logout(
            LogoutRequest request) {


        String accessToken =  request.getAccessToken();


        String jti =  jwtService.extractJti(accessToken);


        Date expiry = jwtService.extractExpire(accessToken);


        long remainingTime = expiry.getTime() - System.currentTimeMillis();


        if (remainingTime > 0) {
            tokenBlacklistService.blacklistToken(jti, remainingTime);
        }

        String username = jwtService.extractUsername(request.getRefreshToken());

        refreshTokenService.deleteRefreshToken(username);

        return "Logout Successful";
    }

}