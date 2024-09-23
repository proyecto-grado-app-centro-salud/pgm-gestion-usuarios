package com.example.microserviciogestionusuarios.security.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.microserviciogestionusuarios.security.dtos.ImagenDto;
import com.example.microserviciogestionusuarios.security.entities.ImagenEntity;
import com.example.microserviciogestionusuarios.security.repositories.ImagenesRepositoryJPA;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


import java.util.regex.*;
import java.util.stream.Collectors;

@Service
public class ImagenesService {
    @Autowired
    ImagenesRepositoryJPA imagenesRepositoryJPA;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ImagenDto> obtenerImagenes(String imageableType, Integer imageableId) {
        List<ImagenEntity>imagenesEntities=imagenesRepositoryJPA.findByImageableTypeAndImageableIdAndDeletedAtIsNull(imageableType, imageableId);
        List<ImagenDto>imagenesDtos=new ArrayList<ImagenDto>();
        for(ImagenEntity imagenEntity:imagenesEntities){
            ImagenDto imagenDto=new ImagenDto().convertirImagenEntityAImagenDto(imagenEntity);
            imagenesDtos.add(imagenDto);
        }
        return imagenesDtos;
    }
    public void guardarImagenes(List<MultipartFile> imagenes, String imageableType, int imageableId) {
        for (MultipartFile imagen : imagenes) {
            if (imagen != null && !imagen.isEmpty()) {
                ImagenEntity imagenEntity = new ImagenEntity();
                imagenEntity.setImageableType(imageableType);
                imagenEntity.setImageableId(imageableId);
                imagenEntity.setNombre(imagen.getOriginalFilename());
                imagenEntity.setTipo(imagen.getContentType());                
                imagenEntity.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNBVh79jGaH-D21r7geT8NSnFWIqqAVEbFvA&s");
                imagenesRepositoryJPA.save(imagenEntity);
            }
        }
    }
    public List<MultipartFile> obtenerImagenesDeArchivos(Map<String, MultipartFile> archivos){
        List<MultipartFile> imagenes = new ArrayList<>();
            for (Map.Entry<String, MultipartFile> entry : archivos.entrySet()) {
                if (entry.getKey().equals("imagen1") || entry.getKey().equals("imagen2") || entry.getKey().equals("imagen3")) {
                    imagenes.add(entry.getValue());
                }
            }
        return imagenes;
    }
    public List<ImagenDto> obtenerImagenesDeParametros(Map<String, String> parametros){
        List<ImagenDto> imagenes = new ArrayList<>();
            for (Map.Entry<String, String> entry : parametros.entrySet()) {
                if (entry.getKey().equals("imagen1") || entry.getKey().equals("imagen2") || entry.getKey().equals("imagen3")) {
                    try{
                        ImagenDto imagenDto = objectMapper.readValue(entry.getValue(), ImagenDto.class);
                        imagenes.add(imagenDto);
                    }catch(Exception e){
                    }
                }
            }
        return imagenes;
    }
    public void actualizarImagenes(Map<String, MultipartFile> allFiles, Map<String, String> params,
            String imagableType, int imageableId) {
        List<ImagenDto>imagenesAEliminar=obtenerImagenesAEliminar(params,imagableType,imageableId);
        List<MultipartFile> imagenes=obtenerImagenesDeArchivos(allFiles);
        guardarImagenes(imagenes,imagableType,imageableId);
        for(ImagenDto imagen: imagenesAEliminar){
            imagenesRepositoryJPA.deleteById(imagen.getIdImagen());
        }
    }
    private List<ImagenDto> obtenerImagenesAEliminar(Map<String, String> params,String imagableType, int imageableId) {
        List<ImagenDto> imagenesElemento=obtenerImagenes(imagableType, imageableId);
        List<ImagenDto> imagenesConservadas=obtenerImagenesDeParametros(params);      
        List<ImagenDto> imagenesAEliminar=quitarImagenes(imagenesElemento, imagenesConservadas);
        return imagenesAEliminar;
        
    }
    private List<ImagenDto> quitarImagenes(List<ImagenDto> imagenes,
            List<ImagenDto> imagenesConservadas) {
        List<ImagenDto> imagenesAEliminar = new ArrayList<>();
        for (ImagenDto imagen : imagenes) {
            boolean estaConservada = imagenesConservadas.stream()
            .anyMatch(imagenConservada -> imagenConservada.getIdImagen().equals(imagen.getIdImagen()));
            if (!estaConservada) {
                imagenesAEliminar.add(imagen);
            }
        }    
        return imagenesAEliminar;
    }  
}
