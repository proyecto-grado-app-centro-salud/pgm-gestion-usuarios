package com.example.microserviciogestionusuarios.security.dtos;

import java.util.Date;
import java.util.List;

import com.example.microserviciogestionusuarios.security.entities.ImagenEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenDto {
    private Integer idImagen;
    private String nombre;
    private String tipo;
    private String url;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    public ImagenDto convertirImagenEntityAImagenDto(ImagenEntity imagenEntity) {
        ImagenDto imagenDto = new ImagenDto();
        imagenDto.setIdImagen(imagenEntity.getIdImagen());
        imagenDto.setNombre(imagenEntity.getNombre());
        imagenDto.setTipo(imagenEntity.getTipo());
        imagenDto.setUrl(imagenEntity.getUrl());
        imagenDto.setCreatedAt(imagenEntity.getCreatedAt());
        imagenDto.setUpdatedAt(imagenEntity.getUpdatedAt());
        imagenDto.setDeletedAt(imagenEntity.getDeletedAt());
        return imagenDto;
    }
}
