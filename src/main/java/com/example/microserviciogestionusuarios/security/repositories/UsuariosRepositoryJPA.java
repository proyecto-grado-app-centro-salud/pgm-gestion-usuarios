package com.example.microserviciogestionusuarios.security.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;


public interface UsuariosRepositoryJPA extends JpaRepository<UsuarioEntity, String>{
    List<UsuarioEntity> findAllByDeletedAtIsNull();
    Optional<UsuarioEntity> findByCiAndDeletedAtIsNull(String ci);
    Optional<UsuarioEntity> findByIdUsuarioAndDeletedAtIsNull(String idUsuario);
    Optional<UsuarioEntity> findByCi(String ci);

}
