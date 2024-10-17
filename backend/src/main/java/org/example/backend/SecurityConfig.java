package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepo userRepo;

    @Value("${app.url}")
    private String appUrl;

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/vocab/").authenticated()
                        .requestMatchers("/api/vocab/login").permitAll()
                        .requestMatchers("/api/vocab/*").authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.ALWAYS
                ))
                .logout(logout -> logout
                        .logoutSuccessUrl(appUrl)
                        .logoutUrl("/api/auth/logout"))
                .oauth2Login(login -> login.defaultSuccessUrl(appUrl+"/"))
                .build();
    }

}
