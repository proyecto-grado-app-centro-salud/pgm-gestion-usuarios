package com.example.microserviciogestionusuarios.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;

@Service
public class RolesService {
    @Autowired
    RolesRepositoryJPA rolesRepositoryJPA;
    public List<RolDto> obtenerRoles() {
        List<RolEntity> rolesEntities = rolesRepositoryJPA.findAll();
        List<RolDto> rolesDtos = rolesEntities.stream()
                                          .map(rolEntity->
                                            new RolDto().convertirRolEntityARolDto(rolEntity) 
                                           )
                                          .collect(Collectors.toList());
        return rolesDtos;
    }
    
}
