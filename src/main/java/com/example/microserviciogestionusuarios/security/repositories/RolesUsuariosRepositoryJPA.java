package com.example.microserviciogestionusuarios.security.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.RolUsuarioEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.modelo.ids_embebidos.RolUsuarioId;

public interface RolesUsuariosRepositoryJPA extends JpaRepository<RolUsuarioEntity, Integer>{
    List<RolUsuarioEntity> findByUsuario(UsuarioEntity usuarioEntity);

    List<RolUsuarioEntity> findByRol(RolEntity rolEntity);

    Optional<RolUsuarioEntity> findOneByUsuarioAndRol(UsuarioEntity usuarioEntity, RolEntity rolEntity);
}
