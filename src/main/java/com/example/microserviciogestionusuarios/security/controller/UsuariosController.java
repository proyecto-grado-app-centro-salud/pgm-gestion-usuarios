package com.example.microserviciogestionusuarios.security.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.microserviciogestionusuarios.security.dtos.UsuarioDto;
import com.example.microserviciogestionusuarios.security.jwt.JwtProvider;
import com.example.microserviciogestionusuarios.security.services.UsuariosService;
import com.example.microserviciogestionusuarios.security.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/v1.0/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);


    @PermitAll
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getUsuarios() {
        try {
            return new ResponseEntity<>(usuariosService.obtenerUsuarios(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PermitAll
    @GetMapping(value = "/{ci}")
    public ResponseEntity<UsuarioDto> getUsuarioByCi(@PathVariable String ci) {
        try {
            UsuarioDto userDto = usuariosService.obtenerUsuarioPorCi(ci);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PermitAll
    @GetMapping(value = "/id/{idUsuario}")
    public ResponseEntity<UsuarioDto> getUsuarioByIdUsuario(@PathVariable String idUsuario) {
        try {
            UsuarioDto userDto = usuariosService.obtenerUsuarioPorId(idUsuario);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PermitAll
    @GetMapping(value = "/codigo-verificacion/{idUsuario}")
    public ResponseEntity<UsuarioDto> obtenerCodigoVerificacion(@PathVariable String idUsuario) {
        try {
            usuariosService.obtenerCodigoVerificacion(idUsuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PermitAll
    @PostMapping("/cambiar-contrasenia")
    public ResponseEntity<Void> cambiarPassword(@RequestBody Map<String, String> body) {
        try{
            userService.cambiarPassword(body.get("username"), body.get("nuevoPassword"), body.get("codigoVerificacion"));
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@RequestParam("data") String data, @RequestParam Map<String, MultipartFile> allFiles) {
        try {
            UsuarioDto userDto = objectMapper.readValue(data, UsuarioDto.class);
            UsuarioDto createdUsuario = usuariosService.crearUsuario(userDto, allFiles);
            return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    @PutMapping(value = "/{idUsuario}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable String idUsuario, @RequestParam("data") String data,
    @RequestParam Map<String, String> params,@RequestParam Map<String, MultipartFile> allFiles) {
        try {
            UsuarioDto userDto = objectMapper.readValue(data, UsuarioDto.class);
            UsuarioDto updatedUsuario = usuariosService.actualizarUsuario(idUsuario, userDto, allFiles,params);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @DeleteMapping(value = "/{idUsuario}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String idUsuario) {
        try {
            usuariosService.eliminarUsuario(idUsuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @PutMapping(value = "/usuario-restaurado/{idUsuario}")
    public ResponseEntity<Void> restaurarUsuario(@PathVariable String idUsuario) {
        try {
            usuariosService.restaurarUsuario(idUsuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
