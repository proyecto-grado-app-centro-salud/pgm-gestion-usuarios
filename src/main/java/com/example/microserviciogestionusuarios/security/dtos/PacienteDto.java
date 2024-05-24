package com.example.microserviciogestionusuarios.security.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PacienteDto {

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String nombres;

    private Date fechaNacimiento;

    private String sexo;

    private String procedencia;

    private Date fechaIngreso;

    private String idiomaHablado;

    private String autoprescedenciaCultural;

    private String ocupacion;

    private String apoyoDesicionAsistenciaMedica;

    private String estadoCivil;

    private String escolaridad;

    private String grupoSanguineo;

    private String ci;

    private String email;

    private String celular;

    private Integer diasSancion;

    private Integer edad;

    private String residencia;

    private String codigoExpedienteClinico;

    private String password;
}
