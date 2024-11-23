package com.example.microserviciogestionusuarios.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microserviciogestionusuarios.security.entities.ImagenEntity;

import java.util.List;

@Repository
public interface ImagenesRepositoryJPA extends JpaRepository<ImagenEntity, Integer> {
    List<ImagenEntity> findByImageableTypeAndImageableIdAndDeletedAtIsNull(String imageableType, String imageableId);
}