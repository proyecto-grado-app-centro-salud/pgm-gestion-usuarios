package com.example.microserviciogestionusuarios.security.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.repositories.PacienteRepository;
import com.example.microserviciogestionusuarios.security.services.PacienteService;

import jakarta.annotation.security.PermitAll;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(path="/pacientes")
public class PacientesController {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    PacienteService pacienteService;

    // @GetMapping()
    // public ResponseEntity<List<PacienteEntity>> obtenerMensajePaciente() {
    //     List<PacienteEntity> listadoPacientes=pacienteRepository.findAll();
    //     return new ResponseEntity<List<PacienteEntity>>(listadoPacientes, HttpStatus.OK);
    // }

    //@PermitAll
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'MEDICO')")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'MEDICO')")
    @GetMapping
    public ResponseEntity<List<PacienteEntity>> listadoPacientes() {
        List<PacienteEntity> listadoPacientes=pacienteRepository.findAll();
        return new ResponseEntity<List<PacienteEntity>>(listadoPacientes, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<String> registroPaciente(PacienteDto pacienteDto) {
        pacienteService.registroPaciente(pacienteDto);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


    // @GetMapping()
    // public @ResponseBody List<HistoriaClinicaEntity> obtenerTodasHistoriasClinicas() {
    //     return historiaClinicaRepositoryJPA.findAll();
    // }
}
