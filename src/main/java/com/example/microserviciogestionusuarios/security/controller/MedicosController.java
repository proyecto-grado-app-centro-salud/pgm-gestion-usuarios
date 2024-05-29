package com.example.microserviciogestionusuarios.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.repositories.MedicoRepository;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path="/medicos")
public class MedicosController {
    @Autowired
    MedicoRepository medicoRepository;

    @PermitAll
    @GetMapping
    public ResponseEntity<List<MedicoEntity>> listadoMedicos() {
        List<MedicoEntity> listadoMedico=medicoRepository.findAll();
        return new ResponseEntity<List<MedicoEntity>>(listadoMedico, HttpStatus.OK);
    }

    @PermitAll
    @GetMapping("/{idMedico}")
    public @ResponseBody MedicoEntity obtenerDetallePaciente(@PathVariable int idMedico) {
        return medicoRepository.findById(idMedico)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la peticion"));
    }

    
}
