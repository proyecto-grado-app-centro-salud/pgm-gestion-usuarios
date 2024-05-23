package com.example.microserviciogestionusuarios.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.microserviciogestionusuarios.security.services.RoleService;

@Component
public class CreateRoles implements CommandLineRunner{
    @Autowired
    private RoleService roleService;
    @Override
    public void run(String... args) throws Exception {
        //roleService.createRoles();
    }
}
