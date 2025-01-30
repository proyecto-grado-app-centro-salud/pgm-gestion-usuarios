package com.example.microserviciogestionusuarios.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;

public interface PacienteRepository extends JpaRepository<PacienteEntity, String>{
    Optional<PacienteEntity> findByEmail(String email);

}
