package com.example.microserviciogestionusuarios.security.dtos;

import java.util.Date;
import java.util.List;

import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Integer idUsuario;
    private String nombres;
    private String ci;
    private String direccion;
    private String celular;
    private String email;
    private String password;
    private String grupoSanguineo;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private String sexo;
    private String estadoCivil;
    private Integer edad;
    private Integer diasSancionPeticionFichaPresencial;
    private String telefono;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private List<ImagenDto> imagenes;

    public UsuarioDto convertirUsuarioEntityAUsuarioDto(UsuarioEntity userEntity) {
        UsuarioDto userDto = new UsuarioDto();
        userDto.setIdUsuario(userEntity.getIdUsuario());
        userDto.setNombres(userEntity.getNombres());
        userDto.setCi(userEntity.getCi());
        userDto.setDireccion(userEntity.getDireccion());
        userDto.setCelular(userEntity.getCelular());
        userDto.setEmail(userEntity.getEmail());
        userDto.setGrupoSanguineo(userEntity.getGrupoSanguineo());
        userDto.setApellidoPaterno(userEntity.getApellidoPaterno());
        userDto.setApellidoMaterno(userEntity.getApellidoMaterno());
        userDto.setFechaNacimiento(userEntity.getFechaNacimiento());
        userDto.setSexo(userEntity.getSexo());
        userDto.setEstadoCivil(userEntity.getEstadoCivil());
        userDto.setEdad(userEntity.getEdad());
        userDto.setDiasSancionPeticionFichaPresencial(userEntity.getDiasSancionPeticionFichaPresencial());
        userDto.setTelefono(userEntity.getTelefono());
        userDto.setCreatedAt(userEntity.getCreatedAt());
        userDto.setUpdatedAt(userEntity.getUpdatedAt());
        userDto.setDeletedAt(userEntity.getDeletedAt());
        return userDto;
    }
}
