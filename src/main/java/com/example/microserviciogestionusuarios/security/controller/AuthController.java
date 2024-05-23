package com.example.microserviciogestionusuarios.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.dtos.ResponseMessageDto;
import com.example.microserviciogestionusuarios.security.dtos.SignInDto;
import com.example.microserviciogestionusuarios.security.dtos.SignUpDto;
import com.example.microserviciogestionusuarios.security.services.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")    
    public ResponseEntity<ResponseMessageDto> signUpUser(@RequestBody @Valid SignUpDto signUpDto,BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        }
        userService.signUpUser(signUpDto);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto("Se ha registrado el usuario con exito"), HttpStatus.OK);
    }
    @PostMapping("/sign-in")    
    public ResponseEntity<ResponseMessageDto> signInUser(@RequestBody @Valid SignInDto signInDto,BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        }
        String accessToken = userService.signInUser(signInDto);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto(accessToken), HttpStatus.OK);
    }
}
