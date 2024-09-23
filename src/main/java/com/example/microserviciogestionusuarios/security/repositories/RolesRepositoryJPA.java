package com.example.microserviciogestionusuarios.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciogestionusuarios.security.entities.RolEntity;

public interface RolesRepositoryJPA extends JpaRepository<RolEntity, Integer>{
}

