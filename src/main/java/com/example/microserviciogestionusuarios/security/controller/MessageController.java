package com.example.microserviciogestionusuarios.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.dtos.ResponseMessageDto;

@RestController
@RequestMapping("/message")
public class MessageController {

    @GetMapping("/paciente-message")
    @PreAuthorize("hasAuthority('PACIENTE')")
    public ResponseEntity<ResponseMessageDto> obtenerMensajePaciente() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol paciente"), HttpStatus.OK);
    }

    @GetMapping("/medico-message")
    @PreAuthorize("hasAuthority('MEDICO')")
    public ResponseEntity<ResponseMessageDto> obtenerMensajeMedico() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol medico"), HttpStatus.OK);
    }

    @GetMapping("/administrador-message")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ResponseMessageDto> obtenerMensajeAdministrador() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol administrador"), HttpStatus.OK);
    }
}