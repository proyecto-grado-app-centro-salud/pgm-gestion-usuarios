// package com.example.microserviciogestionusuarios.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// // import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// // import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// // import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
// // import org.springframework.security.config.http.SessionCreationPolicy;
// // import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// // import com.example.microserviciogestionusuarios.security.jwt.JwtEntryPoint;

// import jakarta.servlet.http.HttpServletRequest;

// import java.util.Arrays;
// import java.util.List;



// @Configuration
// ///@EnableWebSecurity
// public class MainSecurity {

    
//     // @Autowired
//     // private JwtEntryPoint jwtEntryPoint;

//     // @Bean
//     // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     //     http.cors().configurationSource( request -> {
//     //         CorsConfiguration corsConfiguration = new CorsConfiguration();
//     //         corsConfiguration.setAllowedOrigins(List.of("*"));
//     //         corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//     //         corsConfiguration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE","PATCH","OPTIONS"));
//     //         corsConfiguration.setAllowCredentials(true);
//     //         corsConfiguration.addExposedHeader("Message");
//     //         corsConfiguration.setAllowedHeaders(List.of("*"));
//     //         return corsConfiguration;
//     //     }).and().csrf().disable()
//     //             .authorizeRequests()
//     //             .requestMatchers("/fichas-medicas/*","/fichas-medicas","/fichas-medicas/paciente/*","/fichas-medicas/medico/*","/fichas-medicas/detalle/paciente/*","/**")
//     //             .permitAll()
//     //             .anyRequest().authenticated();
//     //     // http.cors().and().csrf().disable()
//     //     //         .authorizeRequests()
//     //     //         .requestMatchers("/fichas-medicas/**","/fichas-medicas","/fichas-medicas/paciente/*","/fichas-medicas/medico/*","/fichas-medicas/detalle/paciente/*","/**")
//     //     //         .permitAll()
//     //     //         .anyRequest().authenticated();
//     //     return http.build();
//     //     // http.cors().and()
//     //     //     .csrf().disable()
//     //     //     .authorizeRequests()
//     //     //     .requestMatchers("/**","/auth/*", "/manage/*", "/api/*", "/auth/sign-in", "/pacientes/**", "/medicos/**", "/v1.0/usuarios","/v1.0/usuarios/**","/v1.0/roles", "/v1.0/roles/**")
//     //     //     .permitAll()
//     //     //     .anyRequest().authenticated()
//     //     //     .and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
//     //     //     .and()
//     //     //     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//     //     // //http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//     //     // return http.build();
//     // }
//     @Bean
// public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     CorsConfiguration configuration = new CorsConfiguration();
//     configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); 
//     configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//     configuration.setAllowCredentials(true); 
//     configuration.setAllowedHeaders(List.of("*"));
//     configuration.addExposedHeader("Message");
//     source.registerCorsConfiguration("/**", configuration);
//     return source;
//     }
// }