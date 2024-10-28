package com.example.microserviciogestionusuarios.security.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.ChangePasswordRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.example.microserviciogestionusuarios.security.dtos.AdministradorDto;
import com.example.microserviciogestionusuarios.security.dtos.MedicoDto;
import com.example.microserviciogestionusuarios.security.dtos.PacienteDto;
import com.example.microserviciogestionusuarios.security.dtos.SignInDto;
import com.example.microserviciogestionusuarios.security.dtos.UsuarioDto;
import com.example.microserviciogestionusuarios.security.entities.AdministradorEntity;
import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.enums.RoleName;
import com.example.microserviciogestionusuarios.security.repositories.AdministradorRepository;
import com.example.microserviciogestionusuarios.security.repositories.MedicoRepository;
import com.example.microserviciogestionusuarios.security.repositories.PacienteRepository;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;

@Service
public class UserService {
    // @Autowired
    // private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;






    @Autowired
    private RolesRepositoryJPA rolesRepositoryJPA;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    public UserService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    // public void createUser(User user){
    // userRepository.save(user);
    // }

    // public void signUpUser(SignUpDto signUpDto){
    // AttributeType attributeType = new
    // AttributeType().withName("email").withValue(signUpDto.getEmail());
    // SignUpRequest signUpRequest = new SignUpRequest();
    // signUpRequest.withClientId(clientId)
    // .withPassword(signUpDto.getPassword())
    // .withUsername(signUpDto.getEmail())
    // .withUserAttributes(attributeType);
    // awsCognitoIdentityProvider.signUp(signUpRequest);

    // User user = new User();
    // user.setName(signUpDto.getName());
    // user.setLastname(signUpDto.getLastname());
    // user.setEmail(signUpDto.getEmail());
    // user.setRole(roleService.findByRoleName(RoleName.ROLE_ADMIN).get());
    // userRepository.save(user);
    // }

    // public void signUpPaciente(PacienteDto pacienteDto) throws Exception {
    //     // Optional<PacienteEntity> pacienteEntityOptional = findByEmailPaciente(pacienteDto.getEmail());
    //     // Optional<MedicoEntity> medicoEntityOptional = findByEmailMedico(pacienteDto.getEmail());
    //     // Optional<AdministradorEntity> administradorEntity = findByEmailAdministrador(pacienteDto.getEmail());
    //     // if (pacienteEntityOptional.isPresent()) {
    //     //     throw new Exception();
    //     // } else {
    //     //     PacienteEntity pacienteEntity = new PacienteEntity();
    //     //     pacienteEntity.setApellidoPaterno(pacienteDto.getApellidoPaterno());
    //     //     pacienteEntity.setApellidoMaterno(pacienteDto.getApellidoMaterno());
    //     //     pacienteEntity.setNombres(pacienteDto.getNombres());
    //     //     pacienteEntity.setFechaNacimiento(pacienteDto.getFechaNacimiento());
    //     //     pacienteEntity.setSexo(pacienteDto.getSexo());
    //     //     pacienteEntity.setProcedencia(pacienteDto.getProcedencia());
    //     //     pacienteEntity.setFechaIngreso(pacienteDto.getFechaIngreso());
    //     //     pacienteEntity.setIdiomaHablado(pacienteDto.getIdiomaHablado());
    //     //     pacienteEntity.setAutoprescedenciaCultural(pacienteDto.getAutoprescedenciaCultural());
    //     //     pacienteEntity.setOcupacion(pacienteDto.getOcupacion());
    //     //     pacienteEntity.setApoyoDesicionAsistenciaMedica(pacienteDto.getApoyoDesicionAsistenciaMedica());
    //     //     pacienteEntity.setEstadoCivil(pacienteDto.getEstadoCivil());
    //     //     pacienteEntity.setEscolaridad(pacienteDto.getEscolaridad());
    //     //     pacienteEntity.setGrupoSanguineo(pacienteDto.getGrupoSanguineo());
    //     //     pacienteEntity.setCi(pacienteDto.getCi());
    //     //     pacienteEntity.setEmail(pacienteDto.getEmail());
    //     //     pacienteEntity.setCelular(pacienteDto.getCelular());
    //     //     pacienteEntity.setDiasSancion(pacienteDto.getDiasSancion());
    //     //     pacienteEntity.setEdad(pacienteDto.getEdad());
    //     //     pacienteEntity.setResidencia(pacienteDto.getResidencia());
    //     //     pacienteEntity.setCodigoExpedienteClinico(pacienteDto.getCodigoExpedienteClinico());
    //     //     pacienteRepository.save(pacienteEntity);
    //     // }

    //     // if (!pacienteEntityOptional.isPresent() && !medicoEntityOptional.isPresent()
    //     //         && !administradorEntity.isPresent()) {
    //     //     registrarCognito(pacienteDto.getEmail(), pacienteDto.getPassword());
    //     // }
    //     registrarUsuarioCognito(pacienteDto);

    // }

    public Optional<PacienteEntity> findByEmailPaciente(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public Optional<MedicoEntity> findByEmailMedico(String email) {
        return medicoRepository.findByEmail(email);
    }

    public Optional<AdministradorEntity> findByEmailAdministrador(String email) {
        return administradorRepository.findByEmail(email);
    }
    private void registrarUsuarioCognito(UsuarioDto usuarioDto) {
        try {
            AttributeType attributeType = new AttributeType().withName("email").withValue(usuarioDto.getEmail());
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withPassword(usuarioDto.getPassword())
                    .withUsername(usuarioDto.getCi())
                    .withUserAttributes(attributeType);

            awsCognitoIdentityProvider.signUp(signUpRequest);
        } catch (AWSCognitoIdentityProviderException e) {
            System.err.println("Error de Cognito: " + e.getErrorMessage());
            throw e;  
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
            throw e; 
        }
    }

    public void signUpMedico(MedicoDto medicoDto) throws Exception {
        Optional<PacienteEntity> pacienteEntityOptional = findByEmailPaciente(medicoDto.getEmail());
        Optional<MedicoEntity> medicoEntityOptional = findByEmailMedico(medicoDto.getEmail());
        Optional<AdministradorEntity> administradorEntity = findByEmailAdministrador(medicoDto.getEmail());
        if (medicoEntityOptional.isPresent()) {
            throw new Exception();
        } else {
            MedicoEntity medicoEntity = new MedicoEntity();
            medicoEntity.setNombres(medicoDto.getNombres());
            medicoEntity.setCi(medicoDto.getCi());
            medicoEntity.setDireccion(medicoDto.getDireccion());
            medicoEntity.setCelular(medicoDto.getCelular());
            medicoEntity.setEmail(medicoDto.getEmail());
            medicoEntity.setAñosExperiencia(medicoDto.getAniosExperiencia());
            medicoEntity.setSalario(medicoDto.getSalario());
            medicoEntity.setFoto(medicoDto.getFoto());
            medicoEntity.setDescripcion(medicoDto.getDescripcion());
            medicoEntity.setGrupoSanguineo(medicoDto.getGrupoSanguineo());
            medicoEntity.setApellidoMaterno(medicoDto.getApellidoMaterno());
            medicoEntity.setApellidoPaterno(medicoDto.getApellidoPaterno());
            medicoRepository.save(medicoEntity);

        }
        if (!pacienteEntityOptional.isPresent() && !medicoEntityOptional.isPresent()
                && !administradorEntity.isPresent()) {
            registrarUsuarioCognito(medicoDto.getEmail(), medicoDto.getPassword());
        }

    }

    public void signUpAdministrador(AdministradorDto administradorDto) throws Exception {
        Optional<PacienteEntity> pacienteEntityOptional = findByEmailPaciente(administradorDto.getEmail());
        Optional<MedicoEntity> medicoEntityOptional = findByEmailMedico(administradorDto.getEmail());
        Optional<AdministradorEntity> administradorEntityOptional = findByEmailAdministrador(
                administradorDto.getEmail());
        if (administradorEntityOptional.isPresent()) {
            throw new Exception();
        } else {
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
        if (!pacienteEntityOptional.isPresent() && !medicoEntityOptional.isPresent()
                && !administradorEntityOptional.isPresent()) {
            registrarUsuarioCognito(administradorDto.getEmail(), administradorDto.getPassword());

        }

    }
    public void registrarUsuarioCognito(String email, String password) {
        try {
            AttributeType attributeType = new AttributeType().withName("email").withValue(email);
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withPassword(password)
                    .withUsername(email)
                    .withUserAttributes(attributeType);

            awsCognitoIdentityProvider.signUp(signUpRequest);
        } catch (AWSCognitoIdentityProviderException e) {
            System.err.println("Error de Cognito: " + e.getErrorMessage());
            throw e;  
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
            throw e; 
        }
    }

    public String iniciarSesionUsuarioCognito(SignInDto signInDto) {
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", signInDto.getEmail());
        authParams.put("PASSWORD", signInDto.getPassword());
        InitiateAuthRequest initialAuthRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);
        try {
            InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initialAuthRequest);
            if (initiateAuthResult.getAuthenticationResult() == null) {
                if (initiateAuthResult.getChallengeName() != null && 
                    initiateAuthResult.getChallengeName().equals("NEW_PASSWORD_REQUIRED")) {                    
                    RespondToAuthChallengeRequest challengeRequest = new RespondToAuthChallengeRequest()
                            .withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                            .withClientId(clientId)
                            .withChallengeResponses(authParams)
                            .withSession(initiateAuthResult.getSession());
                    try {
                        RespondToAuthChallengeResult challengeResult = awsCognitoIdentityProvider.respondToAuthChallenge(challengeRequest);
                        return challengeResult.getAuthenticationResult().getAccessToken();
                    } catch (Exception e) {
                        throw new RuntimeException("Error responding to password change challenge: " + e.getMessage(), e);
                    }
                }
                throw new RuntimeException("Authentication result is null. Check your credentials.");
            }
            return initiateAuthResult.getAuthenticationResult().getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException("Error during sign in: " + e.getMessage(), e);
        }
    }

    public void eliminarRolCognitoUsuario(int idUsuario, int idRol){
        rolesRepositoryJPA.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        AdminRemoveUserFromGroupRequest request = new AdminRemoveUserFromGroupRequest()
                .withUserPoolId(userPoolId)
                .withUsername(Integer.toString(idUsuario))
                .withGroupName(Integer.toString(idUsuario));
        awsCognitoIdentityProvider.adminRemoveUserFromGroup(request);
    }
    public void agregarRolCognitoUsuario(int idUsuario, int idRol){
        rolesRepositoryJPA.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        AdminAddUserToGroupRequest request = new AdminAddUserToGroupRequest()
                .withUserPoolId(userPoolId)
                .withUsername(Integer.toString(idUsuario))
                .withGroupName(Integer.toString(idUsuario));
        awsCognitoIdentityProvider.adminAddUserToGroup(request);
    }
}
