package com.example.microserviciogestionusuarios.security.entities;
import java.util.Date;

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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pacientes")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private int idPaciente;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "procedencia")
    private String procedencia;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "idioma_hablado")
    private String idiomaHablado;

    @Column(name = "autoprescedencia_cultural")
    private String autoprescedenciaCultural;

    @Column(name = "ocupacion")
    private String ocupacion;

    @Column(name = "apoyo_desicion_asistencia_medica")
    private String apoyoDesicionAsistenciaMedica;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "escolaridad")
    private String escolaridad;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;

    @Column(name = "ci")
    private String ci;

    @Column(name = "email")
    private String email;

    @Column(name = "celular")
    private String celular;

    @Column(name = "dias_sancion")
    private Integer diasSancion;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "residencia")
    private String residencia;

    @Column(name = "codigo_expediente_clinico")
    private String codigoExpedienteClinico;

}
