package com.example.microserviciogestionusuarios.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RolesPermisosService {
    public boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ADMINISTRADOR"));
    }
    public boolean hasMedicoRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("MEDICO"));
    }
    public boolean hasPacienteRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("PACIENTE"));
    }
    public boolean hasSuperusuarioRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("SUPERUSUARIO"));
    }


}
