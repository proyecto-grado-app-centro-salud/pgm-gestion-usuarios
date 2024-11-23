package com.example.microserviciogestionusuarios.security.dtos;

import java.util.Date;
import java.util.List;

import com.example.microserviciogestionusuarios.security.entities.RolUsuarioEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto {

    private String idUsuario;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String ci;

    private String direccion;

    private String celular;

    private String email;

    private String descripcion;

    private String grupoSanguineo;

    private String password;

    private List<ImagenDto> imagenes;

    public PacienteDto convertirRolUsuarioEntityAPacienteDto(RolUsuarioEntity rolUsuarioEntity) {
        this.idUsuario=rolUsuarioEntity.getUsuario().getIdUsuario();
        this.nombres=rolUsuarioEntity.getUsuario().getNombres();
        this.apellidoPaterno=rolUsuarioEntity.getUsuario().getApellidoPaterno();
        this.apellidoMaterno=rolUsuarioEntity.getUsuario().getApellidoMaterno();
        this.ci=rolUsuarioEntity.getUsuario().getCi();
        this.direccion=rolUsuarioEntity.getUsuario().getDireccion();
        this.celular=rolUsuarioEntity.getUsuario().getCelular();
        this.email=rolUsuarioEntity.getUsuario().getEmail();
        this.descripcion=rolUsuarioEntity.getDescripcionProfesional();
        return this;
    }
}
