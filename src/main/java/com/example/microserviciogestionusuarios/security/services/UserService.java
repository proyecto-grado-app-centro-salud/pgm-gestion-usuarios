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
import com.example.microserviciogestionusuarios.security.dtos.SignUpDto;
import com.example.microserviciogestionusuarios.security.entities.User;
import com.example.microserviciogestionusuarios.security.enums.RoleName;
import com.example.microserviciogestionusuarios.security.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Autowired
    private RoleService roleService;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    

    public UserService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    public void createUser(User user){
        userRepository.save(user);
    }

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
       
    }
    public void signUpMedico(MedicoDto medicoDto){
       
    }
    public void signUpAdministrador(AdministradorDto administradorDto){
      
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
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
