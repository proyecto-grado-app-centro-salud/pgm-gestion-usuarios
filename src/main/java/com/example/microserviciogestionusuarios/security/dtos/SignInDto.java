package com.example.microserviciogestionusuarios.security.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NotEmpty(message = "El email no puede ser vacío")
    private String email;
    @NotEmpty(message = "El password no puede ser vacío")
    private String password;
}
