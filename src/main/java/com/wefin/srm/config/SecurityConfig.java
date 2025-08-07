package com.wefin.srm.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        http
                .csrf(csrf -> {
                    // Desabilita o CSRF para a API e o console H2
                    csrf.ignoringRequestMatchers(PathRequest.toH2Console( ));
                    csrf.disable(); // Se você ainda precisar desabilitar globalmente
                })
                .headers(headers -> headers
                        // Permite que o console H2 seja renderizado em um frame
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // --- INÍCIO DA CORREÇÃO ---
                        // Permite acesso irrestrito ao console H2
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        // --- FIM DA CORREÇÃO ---

                        // Regras que já tínhamos
                        .requestMatchers("/api/v1/rates").hasRole("ADMIN")
                        .requestMatchers("/api/v1/transactions/exchange").permitAll()
                        .requestMatchers("/api/v1/rates/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults( ));

        return http.build( );
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
