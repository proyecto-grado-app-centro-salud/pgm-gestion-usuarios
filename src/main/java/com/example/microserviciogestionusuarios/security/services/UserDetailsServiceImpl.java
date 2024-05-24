package com.example.microserviciogestionusuarios.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.entities.AdministradorEntity;
import com.example.microserviciogestionusuarios.security.entities.MedicoEntity;
import com.example.microserviciogestionusuarios.security.entities.PacienteEntity;
import com.example.microserviciogestionusuarios.security.entities.UserMain;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        List<String> tipos_usuario = new ArrayList<>();
        PacienteEntity paciente=userService.findByEmailPaciente(email).orElse(null);
        MedicoEntity medico=userService.findByEmailMedico(email).orElse(null);
        AdministradorEntity administrador=userService.findByEmailAdministrador(email).orElse(null);
        if(paciente!=null){
            tipos_usuario.add("PACIENTE");
        }
        if(medico!=null){
            tipos_usuario.add("MEDICO");
        }
        if(administrador!=null){
            tipos_usuario.add("ADMINISTRADOR");
        }
        return UserMain.build(email,tipos_usuario);
    }

    
}
