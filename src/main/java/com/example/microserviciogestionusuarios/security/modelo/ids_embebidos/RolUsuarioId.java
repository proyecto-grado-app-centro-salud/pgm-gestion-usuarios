package com.example.microserviciogestionusuarios.security.modelo.ids_embebidos;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolUsuarioId implements Serializable{
    private Integer id_rol;
    private Integer id_usuario;
}
