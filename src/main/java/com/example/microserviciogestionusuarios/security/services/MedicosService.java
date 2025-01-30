package com.example.microserviciogestionusuarios.security.services;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.RolUsuarioEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.modelo.ids_embebidos.RolUsuarioId;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.RolesUsuariosRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.UsuariosRepositoryJPA;
@Service
public class MedicosService {
    @Autowired
    private RolesUsuariosRepositoryJPA rolesUsuariosRepositoryJPA;
    @Autowired
    private RolesRepositoryJPA rolesRepositoryJPA;
    @Autowired
    private UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    private ImagenesService imagenesService;
    public List<MedicoDto> obtenerMedicosEspecialitas() {
        RolEntity rolEntity = rolesRepositoryJPA.findById(2)
        .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        List<RolUsuarioEntity> listadoMedicosEntity=rolesUsuariosRepositoryJPA.findByRol(rolEntity);
        List<MedicoDto> listadoMedicos=listadoMedicosEntity
        .stream()
        .map(rolUsuarioEntity->{
            MedicoDto medicoDto = new MedicoDto().convertirRolUsuarioEntityAMedicoDto(rolUsuarioEntity);
            medicoDto.setImagenes(imagenesService.obtenerImagenes("usuarios", rolUsuarioEntity.getUsuario().getIdUsuario()));
            return medicoDto;
        })
        .collect(Collectors.toList());
        return listadoMedicos;
    }
    public MedicoDto obtenerMedicoEspecialitas(String idMedico) {
        RolEntity rolEntity = rolesRepositoryJPA.findById(2)
        .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idMedico)
        .orElseThrow(()-> new RuntimeException("Medico no encontrado"));
        RolUsuarioEntity rolUsuarioEntity=rolesUsuariosRepositoryJPA.findOneByUsuarioAndRol(usuarioEntity,rolEntity)
        .orElseThrow(()-> new RuntimeException("Rol usuario no encontrado"));
        MedicoDto medicoDto = new MedicoDto().convertirRolUsuarioEntityAMedicoDto(rolUsuarioEntity);
        medicoDto.setImagenes(imagenesService.obtenerImagenes("usuarios", rolUsuarioEntity.getUsuario().getIdUsuario()));
        return medicoDto;
    }
    
}
