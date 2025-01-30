package com.example.microserviciogestionusuarios.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.RolUsuarioEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.modelo.ids_embebidos.RolUsuarioId;
import com.example.microserviciogestionusuarios.security.repositories.PacienteRepository;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.RolesUsuariosRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.UsuariosRepositoryJPA;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    private RolesUsuariosRepositoryJPA rolesUsuariosRepositoryJPA;
    @Autowired
    private RolesRepositoryJPA rolesRepositoryJPA;
    @Autowired
    private UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    private ImagenesService imagenesService;

    public void registroPaciente(PacienteDto pacienteDto) {
        PacienteEntity pacienteEntity = new PacienteEntity();
        pacienteEntity.setApellidoPaterno(pacienteDto.getApellidoPaterno());
        pacienteEntity.setApellidoMaterno(pacienteDto.getApellidoMaterno());
        pacienteEntity.setNombres(pacienteDto.getNombres());
        pacienteEntity.setGrupoSanguineo(pacienteDto.getGrupoSanguineo());
        pacienteEntity.setCi(pacienteDto.getCi());
        pacienteRepository.save(pacienteEntity);
    }

    public List<PacienteDto> obtenerPacientes() {
        RolEntity rolEntity = rolesRepositoryJPA.findById(1)
        .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        List<RolUsuarioEntity> listadoPacientesEntity=rolesUsuariosRepositoryJPA.findByRol(rolEntity);
        List<PacienteDto> listadoPacientes=listadoPacientesEntity
        .stream()
        .map(rolUsuarioEntity->{
            PacienteDto medicoDto = new PacienteDto().convertirRolUsuarioEntityAPacienteDto(rolUsuarioEntity);
            medicoDto.setImagenes(imagenesService.obtenerImagenes("usuarios", rolUsuarioEntity.getUsuario().getIdUsuario()));
            return medicoDto;
        })
        .collect(Collectors.toList());
        return listadoPacientes;
    }
}
