package com.example.microserviciogestionusuarios.security.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserMain implements UserDetails{

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserMain(String email, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.authorities = authorities;
    }
    public static UserMain build(User user){
        List<GrantedAuthority> authorities = 
        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRolename().name()));
        
        return new UserMain(user.getEmail(), authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    
}
