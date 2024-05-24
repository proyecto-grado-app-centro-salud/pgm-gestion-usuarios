package com.example.microserviciogestionusuarios.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.microserviciogestionusuarios.security.jwt.JwtEntryPoint;
// import com.example.microserviciogestionusuarios.security.jwt.JwtTokenFilter;
import com.example.microserviciogestionusuarios.security.jwt.JwtTokenFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {

    @Autowired
    public JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().configurationSource( request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
            corsConfiguration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addExposedHeader("Message");
            corsConfiguration.setAllowedHeaders(List.of("Authorization","Cache-control", "Content-Type"));
            return corsConfiguration;
        }).and().csrf().disable()
                .authorizeRequests()
                .requestMatchers("/auth/registro-paciente",
                "/auth/registro-medico",
                "/auth/registro-administrador",
                        "/auth/sign-in","/manage/*")
                .permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}