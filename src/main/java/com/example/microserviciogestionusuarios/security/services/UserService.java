package com.example.microserviciogestionusuarios.security.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
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

    // @Value(value = "${aws.cognito.clientId}")
    private String clientId = "4u16uooucre54ll4hcroq3g2jj";

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

    public void signUpPaciente(PacienteDto pacienteDto) throws Exception {
        Optional<PacienteEntity> pacienteEntityOptional = findByEmailPaciente(pacienteDto.getEmail());
        Optional<MedicoEntity> medicoEntityOptional = findByEmailMedico(pacienteDto.getEmail());
        Optional<AdministradorEntity> administradorEntity = findByEmailAdministrador(pacienteDto.getEmail());
        if (pacienteEntityOptional.isPresent()) {
            throw new Exception();
        } else {
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

        if (!pacienteEntityOptional.isPresent() && !medicoEntityOptional.isPresent()
                && !administradorEntity.isPresent()) {
            registrarCognito(pacienteDto.getEmail(), pacienteDto.getPassword());
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
            registrarCognito(medicoDto.getEmail(), medicoDto.getPassword());
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
            registrarCognito(administradorDto.getEmail(), administradorDto.getPassword());

        }

    }
    public void registrarCognito(String email, String password) {
        try {
            AttributeType attributeType = new AttributeType().withName("email").withValue(email);
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withPassword(password)
                    .withUsername(email)
                    .withUserAttributes(attributeType);

            SignUpResult signUpResult = awsCognitoIdentityProvider.signUp(signUpRequest);

        } catch (AWSCognitoIdentityProviderException e) {
            System.err.println("Error de Cognito: " + e.getErrorMessage());
            throw e;  
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
            throw e; 
        }
    }
    // public void registrarCognito(String email, String password) {
    //     AttributeType attributeType = new AttributeType().withName("email").withValue(email);
    //     SignUpRequest signUpRequest = new SignUpRequest();
    //     signUpRequest.withClientId(clientId)
    //             .withPassword(password)
    //             .withUsername(email)
    //             .withUserAttributes(attributeType);
    //     awsCognitoIdentityProvider.signUp(signUpRequest);
    // }

    public String signInUser(SignInDto signInDto) {
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", signInDto.getEmail());
        authParams.put("PASSWORD", signInDto.getPassword());

        InitiateAuthRequest initialAuthRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);

        InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initialAuthRequest);
        return initiateAuthResult.getAuthenticationResult().getAccessToken();
    }

    public Optional<PacienteEntity> findByEmailPaciente(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public Optional<MedicoEntity> findByEmailMedico(String email) {
        return medicoRepository.findByEmail(email);
    }

    public Optional<AdministradorEntity> findByEmailAdministrador(String email) {
        return administradorRepository.findByEmail(email);
    }
    // public Optional<User> findByEmail(String email){
    // return userRepository.findByEmail(email);
    // }
}
