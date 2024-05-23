package com.example.microserviciogestionusuarios.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {
 
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request ->{
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
            corsConfiguration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addExposedHeader("Message");
            corsConfiguration.setAllowedHeaders(List.of("Authorization","Content-Type","Cache-Control"));
            return corsConfiguration;

        }).and()
        .csrf().disable()
        .authorizeRequests()
        .requestMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
