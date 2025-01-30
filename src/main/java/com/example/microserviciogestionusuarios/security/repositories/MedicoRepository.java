package com.example.microserviciogestionusuarios.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;

public interface MedicoRepository extends JpaRepository<MedicoEntity, String>{
    Optional<MedicoEntity> findByEmail(String email);

}
