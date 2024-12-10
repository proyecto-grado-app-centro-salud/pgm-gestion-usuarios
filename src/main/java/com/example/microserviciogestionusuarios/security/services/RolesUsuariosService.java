package com.example.microserviciogestionusuarios.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.RolUsuarioEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.modelo.ids_embebidos.RolUsuarioId;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.RolesUsuariosRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.UsuariosRepositoryJPA;

@Service
public class RolesUsuariosService {
    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    RolesUsuariosRepositoryJPA rolesUsuariosRepositoryJPA;
    @Autowired
    RolesRepositoryJPA rolesRepositoryJPA;
    @Autowired
    CognitoService cognitoService;
    @Autowired
    ImagenesService imagenesService;
    @Autowired
    private RolesPermisosService rolesPermisosService;

    public List<RolDto> obtenerRolesDeUsuario(String idUsuario) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<RolDto> rolesDtos = rolesUsuariosRepositoryJPA.findByUsuario(usuarioEntity)
                                          .stream()
                                          .map(usuarioRolEntity->{
                                            RolDto rolDto = new RolDto().convertirRolEntityARolDto(usuarioRolEntity.getRol()); 
                                            return rolDto;
                                           })
                                          .collect(Collectors.toList());
        return rolesDtos;
    }

    // public void crearRolUsuario(String idUsuario, int idRol,Authentication authentication) throws Exception {
    //     UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
    //     .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    //     RolEntity rolEntity = rolesRepositoryJPA.findById(idRol)
    //     .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

    //     if (idRol==2 && (!rolesPermisosService.hasAdminRole(authentication) || !rolesPermisosService.hasSuperusuarioRole(authentication))) {
    //         throw new Exception("No tienes permisos para asignar roles");
    //     }
    //     if (idRol==1 && (!rolesPermisosService.hasAdminRole(authentication) || !rolesPermisosService.hasSuperusuarioRole(authentication))) {
    //         throw new Exception("No tienes permisos para asignar roles");
    //     }
    //     if (idRol==3 && (!rolesPermisosService.hasSuperusuarioRole(authentication))) {
    //         throw new Exception("No tienes permisos para asignar roles");
    //     }
    //     RolUsuarioEntity rolUsuarioEntity=new RolUsuarioEntity();
    //     rolUsuarioEntity.setRol(rolEntity);
    //     rolUsuarioEntity.setUsuario(usuarioEntity);
    //     rolesUsuariosRepositoryJPA.save(rolUsuarioEntity);
        
    //     cognitoService.agregarRolCognitoUsuario(idUsuario, idRol);
    // }
    public void crearRolUsuario(String idUsuario, int idRol) throws Exception {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RolEntity rolEntity = rolesRepositoryJPA.findById(idRol)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

       
        RolUsuarioEntity rolUsuarioEntity=new RolUsuarioEntity();
        rolUsuarioEntity.setRol(rolEntity);
        rolUsuarioEntity.setUsuario(usuarioEntity);
        rolesUsuariosRepositoryJPA.save(rolUsuarioEntity);
        
        cognitoService.agregarRolCognitoUsuario(idUsuario, idRol);
    }

    public void eliminarRolUsuario(String idUsuario, int idRol) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RolEntity rolEntity = rolesRepositoryJPA.findById(idRol)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        RolUsuarioEntity rolUsuarioEntity=rolesUsuariosRepositoryJPA.findOneByUsuarioAndRol(usuarioEntity,rolEntity) 
        .orElseThrow(() -> new RuntimeException("Rol usuario no encontrado"));

        rolesUsuariosRepositoryJPA.deleteById(rolUsuarioEntity.getIdUsuarioRol());

        cognitoService.eliminarRolCognitoUsuario(idUsuario, idRol);
    }
    
}
