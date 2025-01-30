package com.example.microserviciogestionusuarios.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciogestionusuarios.dtos.ResponseMessageDto;
import com.example.microserviciogestionusuarios.security.dtos.SignInDto;
import com.example.microserviciogestionusuarios.security.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.annotation.security.PermitAll;
@RestController
@RequestMapping(path = "/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class AuthController {
    
    @Autowired
    private UserService userService;




    @PermitAll
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMessageDto> signInUser(@RequestBody @Valid SignInDto signInDto, BindingResult bindingResult){
        try {
            if (bindingResult.hasFieldErrors()) {
                return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
            }
            String accessToken = userService.iniciarSesionUsuarioCognito(signInDto);
            return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto(accessToken), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResponseMessageDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/user-details")
    // public Optional<User> getUserDetails(){
    //     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String email = userDetails.getUsername();
    //     return userService.findByEmail(email);
    // }
    
    // @GetMapping("/user-details")
    // public UserDetailsDto getUserDetails(){
    //     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String email = userDetails.getUsername();
    //     List<String> roles = new ArrayList<>();
    //     UserDetailsDto userDetailsDto=new UserDetailsDto(roles, 0, 0, 0, email, "");
    //     Optional<PacienteEntity> pacienteOptional=userService.findByEmailPaciente(email);
    //     Optional<MedicoEntity> medicoOptional=userService.findByEmailMedico(email);
    //     Optional<AdministradorEntity> administradorOptional=userService.findByEmailAdministrador(email);
    //     if (pacienteOptional.isPresent()) {
    //         userDetailsDto.setCi(pacienteOptional.get().getCi());
    //         userDetailsDto.setIdPaciente(pacienteOptional.get().getIdPaciente());
    //         roles.add("PACIENTE");
    //     }
        
    //     if (medicoOptional.isPresent()) {
    //         userDetailsDto.setCi(medicoOptional.get().getCi());
    //         userDetailsDto.setIdMedico(medicoOptional.get().getIdMedico());
    //         roles.add("MEDICO");
    //     }
        
    //     if (administradorOptional.isPresent()) {
    //         userDetailsDto.setCi(administradorOptional.get().getCi());
    //         userDetailsDto.setIdAdministrador(administradorOptional.get().getIdAdministrador());
    //         roles.add("ADMINISTRADOR");
    //     }
    //     userDetailsDto.setRoles(roles);
    //     return userDetailsDto;
    // }
    // @GetMapping("/detalles-paciente")
    // public Optional<PacienteEntity> getPacienteDetail(){
    //     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String email = userDetails.getUsername();
    //     return userService.findByEmailPaciente(email);
    // }

    // @GetMapping("/detalles-medico")
    // public Optional<MedicoEntity> getMedicoDetails(){
    //     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String email = userDetails.getUsername();
    //     return userService.findByEmailMedico(email);
    // }

    // @GetMapping("/detalles-administrador")
    // public Optional<AdministradorEntity> getAdmnistradorDetails(){
    //     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String email = userDetails.getUsername();
    //     return userService.findByEmailAdministrador(email);
    // }
}
