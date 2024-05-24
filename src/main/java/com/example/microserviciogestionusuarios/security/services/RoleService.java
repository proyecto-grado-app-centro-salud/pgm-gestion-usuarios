// package com.example.microserviciogestionusuarios.security.services;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.microserviciogestionusuarios.security.entities.Role;
// import com.example.microserviciogestionusuarios.security.enums.RoleName;
// import com.example.microserviciogestionusuarios.security.repositories.RoleRepository;

// @Service
// public class RoleService {
//     @Autowired
//     private RoleRepository roleRepository;
    
//     public void createRoles(){
//         Role user=new Role(RoleName.ROLE_USER);
//         Role admin=new Role(RoleName.ROLE_ADMIN);
//         roleRepository.save(user);
//         roleRepository.save(admin);
//     }

//     public Optional<Role> findByRoleName(RoleName rolename){
//         return roleRepository.findByRolename(rolename);
//     }
// }
