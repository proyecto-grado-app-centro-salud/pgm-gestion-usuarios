package com.example.microserviciogestionusuarios.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.repositories.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    public void getPacientes(){
        List<PacienteEntity> listadoPacientes= pacienteRepository.findAll();
    }

    public void registroPaciente(PacienteDto pacienteDto) {
        PacienteEntity pacienteEntity = new PacienteEntity();
        pacienteEntity.setApellidoPaterno(pacienteDto.getApellidoPaterno());
        pacienteEntity.setApellidoMaterno(pacienteDto.getApellidoMaterno());
        pacienteEntity.setNombres(pacienteDto.getNombres());
        pacienteEntity.setFechaNacimiento(pacienteDto.getFechaNacimiento());
        pacienteEntity.setSexo(pacienteDto.getSexo());
        pacienteEntity.setProcedencia(pacienteDto.getProcedencia());
        pacienteEntity.setFechaIngreso(pacienteDto.getFechaIngreso());
        pacienteEntity.setIdiomaHablado(pacienteDto.getIdiomaHablado());
        pacienteEntity.setAutoprescedenciaCultural(pacienteDto.getAutoprescedenciaCultural());
        pacienteEntity.setOcupacion(pacienteDto.getOcupacion());
        pacienteEntity.setApoyoDesicionAsistenciaMedica(pacienteDto.getApoyoDesicionAsistenciaMedica());
        pacienteEntity.setEstadoCivil(pacienteDto.getEstadoCivil());
        pacienteEntity.setEscolaridad(pacienteDto.getEscolaridad());
        pacienteEntity.setGrupoSanguineo(pacienteDto.getGrupoSanguineo());
        pacienteEntity.setCi(pacienteDto.getCi());
        pacienteRepository.save(pacienteEntity);
    }
}
