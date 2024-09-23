package com.example.microserviciogestionusuarios.security.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microserviciogestionusuarios.security.dtos.UsuarioDto;
import com.example.microserviciogestionusuarios.security.services.UsuariosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1.0/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getUsuarios() {
        try {
            return new ResponseEntity<>(usuariosService.obtenerUsuarios(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{ci}")
    public ResponseEntity<UsuarioDto> getUsuarioByCi(@PathVariable String ci) {
        try {
            UsuarioDto userDto = usuariosService.obtenerUsuarioPorCi(ci);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/id/{idUsuario}")
    public ResponseEntity<UsuarioDto> getUsuarioByCi(@PathVariable int idUsuario) {
        try {
            UsuarioDto userDto = usuariosService.obtenerUsurioPorId(idUsuario);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@RequestParam("data") String data, @RequestParam Map<String, MultipartFile> allFiles) {
        try {
            UsuarioDto userDto = objectMapper.readValue(data, UsuarioDto.class);
            UsuarioDto createdUsuario = usuariosService.crearUsuario(userDto, allFiles);
            return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idUsuario}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable int idUsuario, @RequestParam("data") String data,
    @RequestParam Map<String, String> params,@RequestParam Map<String, MultipartFile> allFiles) {
        try {
            UsuarioDto userDto = objectMapper.readValue(data, UsuarioDto.class);
            UsuarioDto updatedUsuario = usuariosService.actualizarUsuario(idUsuario, userDto, allFiles,params);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{idUsuario}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int idUsuario) {
        try {
            usuariosService.eliminarUsuario(idUsuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/usuario-restaurado/{idUsuario}")
    public ResponseEntity<Void> restaurarUsuario(@PathVariable int idUsuario) {
        try {
            usuariosService.restaurarUsuario(idUsuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
