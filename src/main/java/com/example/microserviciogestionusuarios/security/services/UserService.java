package com.example.microserviciogestionusuarios.security.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.example.microserviciogestionusuarios.security.dtos.AdministradorDto;
import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.dtos.SignInDto;
import com.example.microserviciogestionusuarios.security.entities.AdministradorEntity;
import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.enums.RoleName;
import com.example.microserviciogestionusuarios.security.repositories.AdministradorRepository;
import com.example.microserviciogestionusuarios.security.repositories.MedicoRepository;
import com.example.microserviciogestionusuarios.security.repositories.PacienteRepository;

@Service
public class UserService {
    // @Autowired
    // private UserRepository userRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    // @Autowired
    // private RoleService roleService;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    

    public UserService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    // public void createUser(User user){
    //     userRepository.save(user);
    // }

    // public void signUpUser(SignUpDto signUpDto){
    //     AttributeType attributeType = new AttributeType().withName("email").withValue(signUpDto.getEmail());
    //     SignUpRequest signUpRequest = new SignUpRequest();
    //     signUpRequest.withClientId(clientId)
    //     .withPassword(signUpDto.getPassword())
    //     .withUsername(signUpDto.getEmail())
    //     .withUserAttributes(attributeType);
    //     awsCognitoIdentityProvider.signUp(signUpRequest);

    //     User user = new User();
    //     user.setName(signUpDto.getName());
    //     user.setLastname(signUpDto.getLastname());
    //     user.setEmail(signUpDto.getEmail());
    //     user.setRole(roleService.findByRoleName(RoleName.ROLE_ADMIN).get());
    //     userRepository.save(user);
    // }

    public void signUpPaciente(PacienteDto pacienteDto){
        AttributeType attributeType = new AttributeType().withName("email").withValue(pacienteDto.getEmail());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.withClientId(clientId)
        .withPassword(pacienteDto.getPassword())
        .withUsername(pacienteDto.getEmail())
        .withUserAttributes(attributeType);
        awsCognitoIdentityProvider.signUp(signUpRequest);

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
        pacienteEntity.setEmail(pacienteDto.getEmail());
        pacienteEntity.setCelular(pacienteDto.getCelular());
        pacienteEntity.setDiasSancion(pacienteDto.getDiasSancion());
        pacienteEntity.setEdad(pacienteDto.getEdad());
        pacienteEntity.setResidencia(pacienteDto.getResidencia());
        pacienteEntity.setCodigoExpedienteClinico(pacienteDto.getCodigoExpedienteClinico());
        pacienteRepository.save(pacienteEntity);
    }
    public void signUpMedico(MedicoDto medicoDto){
        AttributeType attributeType = new AttributeType().withName("email").withValue(medicoDto.getEmail());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.withClientId(clientId)
        .withPassword(medicoDto.getPassword())
        .withUsername(medicoDto.getEmail())
        .withUserAttributes(attributeType);
        awsCognitoIdentityProvider.signUp(signUpRequest);

        MedicoEntity medicoEntity = new MedicoEntity();
        medicoEntity.setNombre(medicoDto.getNombre());
        medicoEntity.setCi(medicoDto.getCi());
        medicoEntity.setDireccion(medicoDto.getDireccion());
        medicoEntity.setCelular(medicoDto.getCelular());
        medicoEntity.setEmail(medicoDto.getEmail());
        medicoEntity.setAñosExperiencia(medicoDto.getAñosExperiencia());
        medicoEntity.setSalario(medicoDto.getSalario());
        medicoEntity.setFoto(medicoDto.getFoto());
        medicoEntity.setDescripcion(medicoDto.getDescripcion());
        medicoEntity.setGrupoSanguineo(medicoDto.getGrupoSanguineo());
        medicoRepository.save(medicoEntity);

    }
    public void signUpAdministrador(AdministradorDto administradorDto){
        AttributeType attributeType = new AttributeType().withName("email").withValue(administradorDto.getEmail());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.withClientId(clientId)
        .withPassword(administradorDto.getPassword())
        .withUsername(administradorDto.getEmail())
        .withUserAttributes(attributeType);
        awsCognitoIdentityProvider.signUp(signUpRequest);

        AdministradorEntity administradorEntity = new AdministradorEntity();
        administradorEntity.setNombre(administradorDto.getNombre());
        administradorEntity.setCi(administradorDto.getCi());
        administradorEntity.setTelefono(administradorDto.getTelefono());
        administradorEntity.setDepartamento(administradorDto.getDepartamento());
        administradorEntity.setCargo(administradorDto.getCargo());
        administradorEntity.setEmail(administradorDto.getEmail());
        administradorEntity.setGrupoSanguineo(administradorDto.getGrupoSanguineo());
        administradorRepository.save(administradorEntity);
    }
    public String signInUser(SignInDto signInDto){
        final Map<String,String> authParams=new HashMap<>();
        authParams.put("USERNAME", signInDto.getEmail());
        authParams.put("PASSWORD", signInDto.getPassword());        

        InitiateAuthRequest initialAuthRequest= new InitiateAuthRequest()
        .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
        .withClientId(clientId)
        .withAuthParameters(authParams);

        InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initialAuthRequest);
        return initiateAuthResult.getAuthenticationResult().getAccessToken();
    }


    public Optional<PacienteEntity> findByEmailPaciente(String email){
        return pacienteRepository.findByEmail(email);
    }
    public Optional<MedicoEntity> findByEmailMedico(String email){
        return medicoRepository.findByEmail(email);
    }
    public Optional<AdministradorEntity> findByEmailAdministrador(String email){
        return administradorRepository.findByEmail(email);
    }
    // public Optional<User> findByEmail(String email){
    //     return userRepository.findByEmail(email);
    // }
}
