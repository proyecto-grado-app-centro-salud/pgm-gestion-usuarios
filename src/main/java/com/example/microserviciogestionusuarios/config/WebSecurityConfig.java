package com.example.microserviciogestionusuarios.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration
// @EnableWebSecurity
// public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.cors().and() // Habilitar CORS
//             .authorizeRequests()
//             .antMatchers("/**").permitAll() // Configura las rutas públicas
//             .anyRequest().authenticated(); // Otras configuraciones de seguridad
//     }
// }

import java.util.Arrays;
import java.util.Collections;

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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.microserviciogestionusuarios.security.jwt.CustomAccessDeniedHandler;
import com.example.microserviciogestionusuarios.security.jwt.JwtEntryPoint;
import com.example.microserviciogestionusuarios.security.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;


    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // http.csrf().disable().cors((cors)->cors.configurationSource(corsConfigurationSource()));
        // return http.build();
        // http
        // .cors().disable()  // Deshabilita el filtro CORS
        // .csrf().disable()  // Deshabilita la protección CSRF
        // .authorizeRequests()
        //     .requestMatchers("/**").permitAll()  // Permite todas las solicitudes
        // .anyRequest().permitAll(); // Permite todas las solicitudes sin autenticación
        // return http.build();





        // http
        //     .cors()  // Configuración de CORS
        //     .and()
        //     .csrf().disable()  // Deshabilitar CSRF para permitir solicitudes sin autenticación
        //     .authorizeRequests()
        //         .requestMatchers("/**").permitAll()  // Permite todas las rutas sin autenticación
        //         .anyRequest().permitAll();  // Permite cualquier otra solicitud sin autenticación
        // return http.build();


        http
            .cors()  // Configuración de CORS
            .and()
            .csrf().disable()  // Deshabilitar CSRF para permitir solicitudes sin autenticación
            .authorizeRequests()
                .requestMatchers("/auth/*","/manage/*","/v1.0/usuarios/codigo-verificacion/*","/v1.0/usuarios/cambiar-contrasenia").permitAll()  // Permite todas las rutas sin autenticación
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler()).authenticationEntryPoint(jwtEntryPoint)
                .and()  
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);  // Permite cualquier otra solicitud sin autenticación
        return http.build();
    }
// public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     CorsConfiguration configuration = new CorsConfiguration();
//     configuration.setAllowedOrigins(Collections.singletonList("*"));
//     configuration.setAllowCredentials(false);
//     configuration.setAllowPrivateNetwork(false);
//     configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//     source.registerCorsConfiguration("/**", configuration);
//     return source;
//     }
}