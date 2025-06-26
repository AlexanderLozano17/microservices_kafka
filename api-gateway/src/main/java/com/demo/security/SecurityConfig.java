package com.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

	private final JwtUtil jwtUtil;

    /**
     * Constructor que recibe el utilitario JWT.
     *
     * @param jwtUtils Clase utilitaria para manejo de JWT.
     */
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Define el filtro de seguridad y las políticas de acceso.
     *
     * @param http Configuración de HttpSecurity.
     * @return Cadena de filtros de seguridad.
     * @throws Exception Excepción lanzada si ocurre un error de configuración.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil);

        return http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para APIs stateless
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
                .authorizeHttpRequests(auth -> auth
                		.requestMatchers("/api/auth/**").permitAll() // Endpoints públicos no requieren token
                        .requestMatchers("/api/public/**").permitAll() // Endpoints públicos no requieren token
                        .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // Añade el filtro antes de UsernamePassword
                .build();
    }
}
