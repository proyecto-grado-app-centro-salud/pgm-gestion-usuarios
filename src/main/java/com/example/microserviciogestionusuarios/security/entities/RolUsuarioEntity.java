package com.example.microserviciogestionusuarios.security.entities;

import com.example.microserviciogestionusuarios.security.modelo.ids_embebidos.RolUsuarioId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "usuario_rol")
public class RolUsuarioEntity {
    
    // @EmbeddedId
    // private RolUsuarioId rolUsuarioId;
    @Id
    @Column(name = "id_usuario_rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuarioRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    @Column(name = "descripcion_profesional")
    private String descripcionProfesional;

    @Column(name = "codigo_expediente_clinico")
    private String codigoExpedienteClinico;

    @Column(name = "ocupacion")
    private String ocupacion;

}
