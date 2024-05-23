package com.example.microserviciogestionusuarios.security.dtos;

import com.example.microserviciogestionusuarios.security.entities.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SignUpDto {
    @NotEmpty(message = "El nombre no puede ser vacío")
    private String name;

    @NotEmpty(message = "El apellido no puede ser vacío")
    private String lastname;

    @NotEmpty(message = "El email no puede ser vacío")
    private String email;

    @NotEmpty(message = "El contraseña no puede ser vacío")
    private String password;

    // @NotNull
    private Role role;
}
