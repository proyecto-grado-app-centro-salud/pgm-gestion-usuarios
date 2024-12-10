package com.example.microserviciogestionusuarios.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microserviciogestionusuarios.dtos.ResponseMessageDto;
import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.repositories.MedicoRepository;
import com.example.microserviciogestionusuarios.security.services.MedicosService;
import com.example.microserviciogestionusuarios.security.util.ApiResponse;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path="/medicos")
public class MedicosController {
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    MedicosService medicosService;

    @PermitAll
    @GetMapping
    public ResponseEntity<List<MedicoDto>> listadoMedicos() {
        try{
            List<MedicoDto> listadoMedico=medicosService.obtenerMedicosEspecialitas();
            return new ResponseEntity<List<MedicoDto>>(listadoMedico, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }


    @PermitAll
    @GetMapping("/{idMedico}")
    public ResponseEntity<MedicoDto> obtenerDetalleMedico(@PathVariable String idMedico) {
        try{
            MedicoDto medicoDto=medicosService.obtenerMedicoEspecialitas(idMedico);
            return new ResponseEntity<MedicoDto>(medicoDto, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return medicoRepository.findById(idMedico)
        // .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la peticion"));
    }

    @PermitAll
    @PostMapping
    public ResponseEntity<ApiResponse> registroMedico(@RequestBody MedicoEntity medicoEntity) {
        try {
            medicoRepository.save(medicoEntity);
            return new ResponseEntity<ApiResponse>(new ApiResponse("","Ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ApiResponse>(HttpStatus.OK);
        }
       
    }
    @PermitAll
    @PutMapping("/{id}")
    public @ResponseBody MedicoEntity actualizar(@PathVariable Integer id, @RequestBody MedicoEntity actualizada) {
        return medicoRepository.findById(id)
                .map(medico -> {
                    medico.setNombres(actualizada.getNombres());
                    medico.setApellidoPaterno(actualizada.getApellidoPaterno());
                    medico.setApellidoMaterno(actualizada.getApellidoMaterno());
                    medico.setCi(actualizada.getCi());
                    medico.setDireccion(actualizada.getDireccion());
                    medico.setCelular(actualizada.getCelular());
                    medico.setEmail(actualizada.getEmail());
                    medico.setAñosExperiencia(actualizada.getAñosExperiencia());
                    medico.setSalario(actualizada.getSalario());
                    medico.setFoto(actualizada.getFoto());
                    medico.setDescripcion(actualizada.getDescripcion());
                    medico.setGrupoSanguineo(actualizada.getGrupoSanguineo());
                    medicoRepository.save(medico);
                    return medico;
                })
                .orElseGet(() -> {
                    return new MedicoEntity();
                });
    }
        @PermitAll
    @DeleteMapping("/{idMedico}")
    public @ResponseBody ApiResponse eliminarPaciente(@PathVariable int idMedico) {
        medicoRepository.deleteById(idMedico);
        return new ApiResponse("200","Ok");
    }
}
