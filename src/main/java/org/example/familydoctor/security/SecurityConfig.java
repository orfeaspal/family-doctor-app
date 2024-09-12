package org.example.familydoctor.security;

import org.example.familydoctor.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("customUserDetailsService") CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Απενεργοποίηση CSRF για απλότητα
                .authorizeHttpRequests(authorize -> authorize
                        // Επιτρέπονται όλες οι αιτήσεις στις παρακάτω διαδρομές χωρίς αυθεντικοποίηση
                        .requestMatchers(
                                "/api/users/register",
                                "/api/register",
                                "/api/login",
                                "/api/requests",
                                "/api/users",
                                "/api/users/{id}",
                                "/api/users/update/{id}",
                                "/api/users/delete/{id}",
                                "/api/users/doctors",
                                "/api/requests/citizen/{id}",
                                "/api/doctors/{id}",
                                "/api/requests/doctor/{id}",
                                "/api/requests/updateRequest/{id}/{status}",
                                "/swagger-ui/**",        // Εξαιρέσεις για Swagger UI
                                "/v3/api-docs/**"        // Εξαιρέσεις για OpenAPI docs
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new CustomAuthenticationFilter("/api/login", authManager, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
