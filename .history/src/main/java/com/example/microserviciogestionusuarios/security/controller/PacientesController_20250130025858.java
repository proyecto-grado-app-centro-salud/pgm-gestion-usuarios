package com.example.microserviciogestionusuarios.security.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.repositories.PacienteRepository;
import com.example.microserviciogestionusuarios.security.services.PacienteService;
import com.example.microserviciogestionusuarios.security.util.ApiResponse;

import jakarta.annotation.security.PermitAll;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path="/pacientes")
public class PacientesController {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    PacienteService pacienteService;

    //@PermitAll
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'MEDICO')")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'MEDICO')")

    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR','MEDICO')")
    @GetMapping
    public ResponseEntity<List<PacienteDto>> listadoPacientes() {
        try{
            List<PacienteDto> listadoPacientes=pacienteService.obtenerPacientes();
            return new ResponseEntity<List<PacienteDto>>(listadoPacientes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'MEDICO')")
    @PermitAll
    @GetMapping("/{idPaciente}")
    public @ResponseBody PacienteEntity obtenerDetallePaciente(@PathVariable String idPaciente) {
        return pacienteRepository.findById(idPaciente)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la peticion"));
    }
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ApiResponse> registroPaciente(@RequestBody PacienteDto pacienteDto) {
        pacienteService.registroPaciente(pacienteDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse("","OK"), HttpStatus.OK);
    }
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR')")
    @DeleteMapping("/{idPaciente}")
    public @ResponseBody ApiResponse eliminarPaciente(@PathVariable String idPaciente) {
        pacienteRepository.deleteById(idPaciente);
        return new ApiResponse("200","Ok");
    }
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @PreAuthorize("hasAnyAuthority('SUPERUSUARIO','ADMINISTRADOR')")
    @PutMapping("/{id}")
    public @ResponseBody PacienteEntity actualizarPaciente(@PathVariable String id, @RequestBody PacienteEntity actualizada) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setApellidoPaterno(actualizada.getApellidoPaterno());
                    paciente.setApellidoMaterno(actualizada.getApellidoMaterno());
                    paciente.setNombres(actualizada.getNombres());
                    paciente.setFechaNacimiento(actualizada.getFechaNacimiento());
                    paciente.setSexo(actualizada.getSexo());
                    paciente.setProcedencia(actualizada.getProcedencia());
                    paciente.setFechaIngreso(actualizada.getFechaIngreso());
                    paciente.setIdiomaHablado(actualizada.getIdiomaHablado());
                    paciente.setAutoprescedenciaCultural(actualizada.getAutoprescedenciaCultural());
                    paciente.setOcupacion(actualizada.getOcupacion());
                    paciente.setApoyoDesicionAsistenciaMedica(actualizada.getApoyoDesicionAsistenciaMedica());
                    paciente.setEstadoCivil(actualizada.getEstadoCivil());
                    paciente.setEscolaridad(actualizada.getEscolaridad());
                    paciente.setGrupoSanguineo(actualizada.getGrupoSanguineo());
                    paciente.setCi(actualizada.getCi());
                    paciente.setEmail(actualizada.getEmail());
                    paciente.setCelular(actualizada.getCelular());
                    paciente.setDiasSancion(actualizada.getDiasSancion());
                    paciente.setEdad(actualizada.getEdad());
                    paciente.setResidencia(actualizada.getResidencia());
                    paciente.setCodigoExpedienteClinico(actualizada.getCodigoExpedienteClinico());
                    pacienteRepository.save(paciente);
                    return paciente;
                })
                .orElseGet(() -> {
                    return new PacienteEntity();
                });
    }
}
