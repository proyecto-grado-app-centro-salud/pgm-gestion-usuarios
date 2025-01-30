package com.example.microserviciogestionusuarios.security.entities;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "medicos")
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private String idMedico;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "ci")
    private String ci;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "celular")
    private String celular;

    @Column(name = "email")
    private String email;

    @Column(name = "años_experiencia")
    private Integer añosExperiencia;

    @Column(name = "salario")
    private Float salario;

    @Column(name = "foto")
    private String foto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;
}
