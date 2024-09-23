package com.example.microserviciogestionusuarios.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.RolUsuarioEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.modelo.ids_embebidos.RolUsuarioId;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.RolesUsuariosRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.UsuariosRepositoryJPA;

@Service
public class RolesUsuariosService {
    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    RolesUsuariosRepositoryJPA rolesUsuariosRepositoryJPA;
    @Autowired
    RolesRepositoryJPA rolesRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    public List<RolDto> obtenerRolesDeUsuario(int idUsuario) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<RolDto> rolesDtos = rolesUsuariosRepositoryJPA.findByUsuario(usuarioEntity)
                                          .stream()
                                          .map(usuarioRolEntity->{
                                            RolDto rolDto = new RolDto().convertirRolEntityARolDto(usuarioRolEntity.getRol()); 
                                            return rolDto;
                                           })
                                          .collect(Collectors.toList());
        return rolesDtos;
    }

    public void crearRolUsuario(int idUsuario, int idRol) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RolEntity rolEntity = rolesRepositoryJPA.findById(idRol)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        RolUsuarioEntity rolUsuarioEntity=new RolUsuarioEntity(new RolUsuarioId(rolEntity.getIdRol(),usuarioEntity.getIdUsuario()),rolEntity,usuarioEntity);
        rolesUsuariosRepositoryJPA.save(rolUsuarioEntity);
    }

    public void eliminarRolUsuario(int idUsuario, int idRol) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RolEntity rolEntity = rolesRepositoryJPA.findById(idRol)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rolesUsuariosRepositoryJPA.deleteById(new RolUsuarioId(usuarioEntity.getIdUsuario(),rolEntity.getIdRol()));
    }
    
}
