package com.example.microserviciogestionusuarios.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.dtos.ResponseMessageDto;
import com.example.microserviciogestionusuarios.security.dtos.AdministradorDto;
import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.dtos.SignInDto;
import com.example.microserviciogestionusuarios.security.entities.AdministradorEntity;
import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.services.UserDetailsServiceImpl;
import com.example.microserviciogestionusuarios.security.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    // @PostMapping("/sign-up")    
    // public ResponseEntity<ResponseMessageDto> signUpUser(@RequestBody @Valid SignUpDto signUpDto,BindingResult bindingResult){
    //     if(bindingResult.hasFieldErrors()){
    //         return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
    //     }
    //     userService.signUpUser(signUpDto);
    //     return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto("Se ha registrado el usuario con exito"), HttpStatus.OK);
    // }
    @PostMapping("/registro-paciente")    
    public ResponseEntity<ResponseMessageDto> signUpPaciente(@RequestBody PacienteDto pacienteDto){
        // if(bindingResult.hasFieldErrors()){
        //     return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        // }
        userService.signUpPaciente(pacienteDto);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto("Se ha registrado el usuario con exito"), HttpStatus.OK);
    }
    @PostMapping("/registro-medico")    
    public ResponseEntity<ResponseMessageDto> signUpMedico(@RequestBody @Valid MedicoDto medicoDto,BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        }
        userService.signUpMedico(medicoDto);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto("Se ha registrado el usuario con exito"), HttpStatus.OK);
    }
    @PostMapping("/registro-administrador")    
    public ResponseEntity<ResponseMessageDto> signUpAdministrador(@RequestBody @Valid AdministradorDto administradorDto,BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        }
        userService.signUpAdministrador(administradorDto);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto("Se ha registrado el usuario con exito"), HttpStatus.OK);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMessageDto> signInUser(@RequestBody @Valid SignInDto signInDto, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        String accessToken = userService.signInUser(signInDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInDto.getEmail());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto(accessToken), HttpStatus.OK);
    }

    // @GetMapping("/user-details")
    // public Optional<User> getUserDetails(){
    //     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String email = userDetails.getUsername();
    //     return userService.findByEmail(email);
    // }
    
    @GetMapping("/user-details")
    public List<String> getUserDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        List<String> roles = new ArrayList<>();
        if (userService.findByEmailPaciente(email).isPresent()) {
            roles.add("PACIENTE");
        }
        
        if (userService.findByEmailMedico(email).isPresent()) {
            roles.add("MEDICO");
        }
        
        if (userService.findByEmailAdministrador(email).isPresent()) {
            roles.add("ADMINISTRADOR");
        }
        return roles;
    }
    @GetMapping("/detalles-paciente")
    public Optional<PacienteEntity> getPacienteDetail(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userService.findByEmailPaciente(email);
    }

    @GetMapping("/detalles-medico")
    public Optional<MedicoEntity> getMedicoDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userService.findByEmailMedico(email);
    }

    @GetMapping("/detalles-administrador")
    public Optional<AdministradorEntity> getAdmnistradorDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userService.findByEmailAdministrador(email);
    }
}
