package com.example.microserviciogestionusuarios.security.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MedicoDto {
    private int idMedico;

    private String nombre;

    private String ci;

    private String direccion;

    private String celular;

    private String correo;

    private float añosExperiencia;

    private float salario;

    private String foto;

    private String descripcion;

    private String grupoSanguineo;

    private String password;
}
