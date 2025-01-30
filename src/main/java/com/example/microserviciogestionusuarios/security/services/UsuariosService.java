package com.example.microserviciogestionusuarios.security.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.example.microserviciogestionusuarios.security.dtos.ImagenDto;
import com.example.microserviciogestionusuarios.security.dtos.RolDto;
import com.example.microserviciogestionusuarios.security.dtos.UsuarioDto;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.repositories.UsuariosRepositoryJPA;
import com.example.microserviciogestionusuarios.security.util.exceptions.BusinessValidationException;

@Service
public class UsuariosService {
    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;

    @Autowired
    ImagenesService imagenesService;

    @Autowired
    CognitoService cognitoService;

    @Autowired
    RolesUsuariosService rolesUsuariosService;

    @Autowired
    EmailService emailService;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;
    
    public List<UsuarioDto> obtenerUsuarios() {
        List<UsuarioEntity> usuariosEntities = usuariosRepositoryJPA.findAll();
        List<UsuarioDto> usuariosDtos = new ArrayList<>();
        for (UsuarioEntity usuarioEntity : usuariosEntities) {
            UsuarioDto usuarioDto = new UsuarioDto().convertirUsuarioEntityAUsuarioDto(usuarioEntity);
            List<ImagenDto> imagenes = imagenesService.obtenerImagenes("usuarios", usuarioEntity.getIdUsuario());
            usuarioDto.setImagenes(imagenes);
            usuariosDtos.add(usuarioDto);
        }
        return usuariosDtos;
    }
     public UsuarioDto obtenerUsuarioPorCi(String ci) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByCiAndDeletedAtIsNull(ci)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioDto usuarioDto = new UsuarioDto().convertirUsuarioEntityAUsuarioDto(usuarioEntity);
        List<ImagenDto> imagenes = imagenesService.obtenerImagenes("usuarios", usuarioEntity.getIdUsuario());
        usuarioDto.setImagenes(imagenes);
        return usuarioDto;
    }

    public UsuarioDto crearUsuario(UsuarioDto usuarioDto, Map<String, MultipartFile> allFiles) {
        Optional<UsuarioEntity> usuarioEntityExistente = usuariosRepositoryJPA.findByCiAndDeletedAtIsNull(usuarioDto.getCi());
        if(!usuarioEntityExistente.isPresent()){
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setIdUsuario((usuarioDto.getCi()));
            usuarioEntity.setNombres(usuarioDto.getNombres());
            usuarioEntity.setCi(usuarioDto.getCi());
            usuarioEntity.setDireccion(usuarioDto.getDireccion());
            usuarioEntity.setCelular(usuarioDto.getCelular());
            usuarioEntity.setEmail(usuarioDto.getEmail());
            usuarioEntity.setGrupoSanguineo(usuarioDto.getGrupoSanguineo());
            usuarioEntity.setApellidoPaterno(usuarioDto.getApellidoPaterno());
            usuarioEntity.setApellidoMaterno(usuarioDto.getApellidoMaterno());
            usuarioEntity.setFechaNacimiento(usuarioDto.getFechaNacimiento());
            usuarioEntity.setSexo(usuarioDto.getSexo());
            usuarioEntity.setEstadoCivil(usuarioDto.getEstadoCivil());
            usuarioEntity.setEdad(usuarioDto.getEdad());
            usuarioEntity.setTelefono(usuarioDto.getTelefono());

            UsuarioEntity savedEntity = usuariosRepositoryJPA.save(usuarioEntity);

            List<MultipartFile> imagenes = imagenesService.obtenerImagenesDeArchivos(allFiles);
            imagenesService.guardarImagenes(imagenes, "usuarios", savedEntity.getIdUsuario());
            cognitoService.registrarUsuarioCognito(usuarioDto);
            return new UsuarioDto().convertirUsuarioEntityAUsuarioDto(savedEntity);

        }
        throw new RuntimeException("Usuario ya existe");
    }

    public UsuarioDto actualizarUsuario(String idUsuario, UsuarioDto usuarioDto, Map<String, MultipartFile> allFiles,Map<String, String> params) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioEntity.setNombres(usuarioDto.getNombres());
        usuarioEntity.setDireccion(usuarioDto.getDireccion());
        usuarioEntity.setCelular(usuarioDto.getCelular());
        usuarioEntity.setEmail(usuarioDto.getEmail());
        usuarioEntity.setGrupoSanguineo(usuarioDto.getGrupoSanguineo());
        usuarioEntity.setApellidoPaterno(usuarioDto.getApellidoPaterno());
        usuarioEntity.setApellidoMaterno(usuarioDto.getApellidoMaterno());
        usuarioEntity.setFechaNacimiento(usuarioDto.getFechaNacimiento());
        usuarioEntity.setSexo(usuarioDto.getSexo());
        usuarioEntity.setEstadoCivil(usuarioDto.getEstadoCivil());
        usuarioEntity.setEdad(usuarioDto.getEdad());
        usuarioEntity.setTelefono(usuarioDto.getTelefono());

        UsuarioEntity updatedEntity = usuariosRepositoryJPA.save(usuarioEntity);
        imagenesService.actualizarImagenes(allFiles, params, "usuarios", usuarioEntity.getIdUsuario());

        return new UsuarioDto().convertirUsuarioEntityAUsuarioDto(updatedEntity);
    }

    public void eliminarUsuario(String idUsuario) throws Exception {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<RolDto>rolesUsuario=rolesUsuariosService.obtenerRolesDeUsuario(usuarioEntity.getIdUsuario());
        boolean tieneRolSuperUsuario = rolesUsuario.stream()
                .anyMatch(rol -> "SUPERUSUARIO".equals(rol.getNombre()));
        if(tieneRolSuperUsuario) throw new BusinessValidationException("No se puede eliminar superusuario");
        usuarioEntity.markAsDeleted();
        usuariosRepositoryJPA.save(usuarioEntity);
        cognitoService.deshabilitarUsuarioCognito(idUsuario);
    }

    public void restaurarUsuario(String idUsuario) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioEntity.setDeletedAt(null);;
        usuariosRepositoryJPA.save(usuarioEntity);
        cognitoService.habilitarUsuarioCognito(idUsuario);
    }
    public UsuarioDto obtenerUsuarioPorId(String idUsuario) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioDto usuarioDto = new UsuarioDto().convertirUsuarioEntityAUsuarioDto(usuarioEntity);
        List<ImagenDto> imagenes = imagenesService.obtenerImagenes("usuarios", usuarioEntity.getIdUsuario());
        usuarioDto.setImagenes(imagenes);
        return usuarioDto;
    }
    public void obtenerCodigoVerificacion(String idUsuario) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        final String codigoVerificacion=UUID.randomUUID().toString();
        emailService.sendSimpleEmail(usuarioEntity.getEmail(), "Codigo de verificacion",codigoVerificacion);
        usuarioEntity.setCodigoVerificacion(codigoVerificacion);
        usuariosRepositoryJPA.save(usuarioEntity);
    }
}
