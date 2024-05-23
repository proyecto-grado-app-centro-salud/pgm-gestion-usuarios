package com.example.microserviciogestionusuarios.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.microserviciogestionusuarios.security.entities.User;
import com.example.microserviciogestionusuarios.security.entities.UserMain;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email "+email));
        return UserMain.build(user);
    }

    
}
