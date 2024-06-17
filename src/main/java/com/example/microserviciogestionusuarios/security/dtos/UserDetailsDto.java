package com.example.microserviciogestionusuarios.security.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    List<String> roles;
    int idPaciente;
    int idMedico;
    int idAdministrador;
    String email;
    String ci;
}
