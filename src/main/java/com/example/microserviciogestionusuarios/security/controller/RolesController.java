package com.example.microserviciogestionusuarios.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.dtos.UsuarioDto;
import com.example.microserviciogestionusuarios.security.services.RolesService;
import com.example.microserviciogestionusuarios.security.services.UsuariosService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/v1.0/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;
    @PermitAll
    @GetMapping()
    public ResponseEntity<List<RolDto>> getRoles() {
        try {
            return new ResponseEntity<>(rolesService.obtenerRoles(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
