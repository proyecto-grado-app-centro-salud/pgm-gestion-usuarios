package com.example.microserviciogestionusuarios.security.dtos;

import com.example.microserviciogestionusuarios.security.entities.RolEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolDto {
    private int idRol;
    private String nombre;
    public RolDto convertirRolEntityARolDto(RolEntity rolEntity){
        RolDto rolDto=new RolDto();
        rolDto.setIdRol(rolEntity.getIdRol());
        rolDto.setNombre(rolEntity.getNombre());
        return rolDto;
    }
}
