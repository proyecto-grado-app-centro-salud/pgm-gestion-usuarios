package com.example.microserviciogestionusuarios.security.entities;
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
@Table(name = "administradores")
public class AdministradorEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrador")
    private int idAdministrador;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ci")
    private String ci;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "correo")
    private String correo;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;
}
