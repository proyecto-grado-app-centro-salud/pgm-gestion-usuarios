package com.example.microserviciogestionusuarios.security.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AdministradorDto {
    private int idAdministrador;

    private String nombre;

    private String ci;

    private String telefono;

    private String departamento;

    private String cargo;

    private String correo;

    private String grupoSanguineo;

    private String password;
}
