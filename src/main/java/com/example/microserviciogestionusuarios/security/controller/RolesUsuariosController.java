package com.example.microserviciogestionusuarios.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.services.RolesUsuariosService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/v1.0/usuarios")
public class RolesUsuariosController {
    @Autowired
    private RolesUsuariosService rolesUsuariosService;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    @GetMapping(value = "/{idUsuario}/roles")
    public ResponseEntity<List<RolDto>> getRolesUsuario(@PathVariable String idUsuario) {
        try {
            return new ResponseEntity<>(rolesUsuariosService.obtenerRolesDeUsuario(idUsuario), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // @PostMapping(value = "/{idUsuario}/roles/{idRol}")
    // public ResponseEntity<Void> createRolUsuario(@PathVariable String idUsuario,@PathVariable int idRol,Authentication authentication) {
    //     try {

    //         rolesUsuariosService.crearRolUsuario(idUsuario, idRol,authentication);
    //         return new ResponseEntity<>(HttpStatus.OK);
    //     } catch (Exception e) {
    //         e.printStackTrace();

    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    @PostMapping(value = "/{idUsuario}/roles/{idRol}")
    public ResponseEntity<Void> createRolUsuario(@PathVariable String idUsuario,@PathVariable int idRol) {
        try {

            rolesUsuariosService.crearRolUsuario(idUsuario, idRol);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    @DeleteMapping("/{idUsuario}/roles/{idRol}")
    public ResponseEntity<Void> deleteRolUsuario(@PathVariable String idUsuario,@PathVariable int idRol) {
        try {
            rolesUsuariosService.eliminarRolUsuario(idUsuario,idRol);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
