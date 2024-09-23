package com.example.microserviciogestionusuarios.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.services.RolesUsuariosService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1.0/usuarios")
public class RolesUsuariosController {
    @Autowired
    private RolesUsuariosService rolesUsuariosService;

    @GetMapping(value = "/{idUsuario}/roles")
    public ResponseEntity<List<RolDto>> getRolesUsuario(@PathVariable int idUsuario) {
        try {
            return new ResponseEntity<>(rolesUsuariosService.obtenerRolesDeUsuario(idUsuario), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/{idUsuario}/roles/{idRol}")
    public ResponseEntity<Void> createRolUsuario(@PathVariable int idUsuario,@PathVariable int idRol) {
        try {
            rolesUsuariosService.crearRolUsuario(idUsuario, idRol);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idUsuario}/roles/{idRol}")
    public ResponseEntity<Void> deleteRolUsuario(@PathVariable int idUsuario,@PathVariable int idRol) {
        try {
            rolesUsuariosService.eliminarRolUsuario(idUsuario,idRol);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
