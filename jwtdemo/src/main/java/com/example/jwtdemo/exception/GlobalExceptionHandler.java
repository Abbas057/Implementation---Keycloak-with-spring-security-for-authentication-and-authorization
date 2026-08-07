package com.example.jwtdemo.exception;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleUserNotFound(
            UserNotFoundException exception,
            HttpServletRequest request) {


        ErrorResponse error =
                new ErrorResponse(
                        404,
                        exception.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneralException(
            Exception exception,
            HttpServletRequest request) {


        ErrorResponse error =
                new ErrorResponse(
                        500,
                        "Internal server error",
                        request.getRequestURI(),
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

}