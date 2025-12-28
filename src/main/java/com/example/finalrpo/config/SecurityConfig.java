package com.example.finalrpo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. Handle Unauthorized access by returning 401 instead of a redirect
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        http.authorizeHttpRequests(auth -> auth
                // 2. Publicly accessible paths
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/error", "/css/**", "/js/**").permitAll()

                // 3. Admin-only sections
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")

                // 4. Fine-grained HTTP Method control for Tickets/Comments
                // Anyone can view tickets (GET)
                .requestMatchers(HttpMethod.GET, "/api/tickets/**", "/api/comments/**").permitAll()

                // Only Admins or Support can DELETE
                .requestMatchers(HttpMethod.DELETE, "/api/tickets/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPPORT_AGENT")

                // Users, Support, and Admin can create/update (POST/PUT)
                .requestMatchers(HttpMethod.POST, "/api/tickets/**", "/api/comments/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_SUPPORT_AGENT", "ROLE_ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/tickets/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_SUPPORT_AGENT", "ROLE_ADMIN")

                // 5. Catch-all: Secure everything else
                .anyRequest().authenticated()
        );

        // 6. Disable stateful features for the REST API
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        // 7. Keep Basic Auth for Postman testing
        http.httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}