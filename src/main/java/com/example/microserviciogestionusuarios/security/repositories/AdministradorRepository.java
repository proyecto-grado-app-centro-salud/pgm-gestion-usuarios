package com.example.microserviciogestionusuarios.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciogestionusuarios.security.entities.AdministradorEntity;

public interface AdministradorRepository extends JpaRepository<AdministradorEntity, Integer>{
    Optional<AdministradorEntity> findByEmail(String email);

}
