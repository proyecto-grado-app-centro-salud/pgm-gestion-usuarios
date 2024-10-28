package com.example.microserviciogestionusuarios.security.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminDisableUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminEnableUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.example.microserviciogestionusuarios.security.dtos.SignInDto;
import com.example.microserviciogestionusuarios.security.dtos.UsuarioDto;
import com.example.microserviciogestionusuarios.security.entities.RolEntity;
import com.example.microserviciogestionusuarios.security.entities.UsuarioEntity;
import com.example.microserviciogestionusuarios.security.repositories.RolesRepositoryJPA;
import com.example.microserviciogestionusuarios.security.repositories.UsuariosRepositoryJPA;

@Service
public class CognitoService {
    @Autowired
    private RolesRepositoryJPA rolesRepositoryJPA;

    @Autowired
    private UsuariosRepositoryJPA usuariosRepositoryJPA;

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value(value = "${aws.cognito.clientId}")
    String clientId;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;
    public CognitoService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }
    public void registrarUsuarioCognito(UsuarioDto usuarioDto) {
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
        RolEntity rolEntity = rolesRepositoryJPA.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        AdminRemoveUserFromGroupRequest request = new AdminRemoveUserFromGroupRequest()
                .withUserPoolId(userPoolId)
                .withUsername(usuarioEntity.getCi())
                .withGroupName(rolEntity.getNombre());
        awsCognitoIdentityProvider.adminRemoveUserFromGroup(request);
    }
    public void agregarRolCognitoUsuario(int idUsuario, int idRol){
        RolEntity rolEntity = rolesRepositoryJPA.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        AdminAddUserToGroupRequest request = new AdminAddUserToGroupRequest()
                .withUserPoolId(userPoolId)
                .withUsername(usuarioEntity.getCi())
                .withGroupName(rolEntity.getNombre());
        awsCognitoIdentityProvider.adminAddUserToGroup(request);
    }
    public void habilitarUsuarioCognito(int idUsuario){
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        AdminEnableUserRequest request = new AdminEnableUserRequest()
            .withUserPoolId(userPoolId)
            .withUsername(usuarioEntity.getCi());

        awsCognitoIdentityProvider.adminEnableUser(request);
    }

    public void deshabilitarUsuarioCognito(int idUsuario){
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        AdminDisableUserRequest request = new AdminDisableUserRequest()
            .withUserPoolId(userPoolId)
            .withUsername(usuarioEntity.getCi());

        awsCognitoIdentityProvider.adminDisableUser(request);
    }
}
