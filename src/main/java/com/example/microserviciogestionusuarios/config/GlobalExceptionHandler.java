package com.example.microserviciogestionusuarios.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.microserviciogestionusuarios.security.util.exceptions.BusinessValidationException;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<String> handleBusinessValidationException(BusinessValidationException businessValidationException){
        businessValidationException.printStackTrace();
        return new ResponseEntity<>(businessValidationException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>("Ha ocurrido un error inesperado en el servidor.",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
