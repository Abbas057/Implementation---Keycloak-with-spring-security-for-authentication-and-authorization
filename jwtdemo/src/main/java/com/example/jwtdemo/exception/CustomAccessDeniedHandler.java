package com.example.jwtdemo.exception;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class CustomAccessDeniedHandler
        implements AccessDeniedHandler {


    private final ObjectMapper objectMapper =
            new ObjectMapper();


    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception)
            throws IOException, ServletException {


        ErrorResponse error =
                new ErrorResponse(
                        403,
                        "Access denied",
                        request.getRequestURI(),
                        LocalDateTime.now()
                );


        response.setStatus(403);

        response.setContentType(
                "application/json"
        );


        response.getWriter()
                .write(
                        objectMapper.writeValueAsString(error)
                );
    }
}