package com.espe.msvc.usuarios.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((authHttp) -> authHttp
                .requestMatchers(HttpMethod.GET,"/api/Usuario/authorized").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/Usuario/ObtenerTodosLosUsuarios").hasAnyAuthority("SCOPE_read","SCOPE_write")
                .requestMatchers(HttpMethod.GET,"/api/Usuario/ObtenerUsuarioId/{id}").hasAnyAuthority("SCOPE_read","SCOPE_write")
                .requestMatchers(HttpMethod.GET,"/api/Usuario/CheckEmailExistente").hasAnyAuthority("SCOPE_read","SCOPE_write")
                .requestMatchers(HttpMethod.POST,"/api/Usuario/CrearUsuario").hasAuthority("SCOPE_write")
                .requestMatchers(HttpMethod.DELETE,"/api/Usuario/EliminarUsuario/{id}").hasAuthority("SCOPE_write")
                .requestMatchers(HttpMethod.PUT,"/api/Usuario/EditarUsuario/{id}").hasAuthority("SCOPE_write")
                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/msvc-usuarios"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults()));
        return http.build();
    }
}
