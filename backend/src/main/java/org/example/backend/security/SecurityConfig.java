package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import java.util.List;

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
//                        .requestMatchers("/api/vocab").authenticated()
                        .requestMatchers("/api/vocab/login").permitAll()
                        .requestMatchers("/api/vocab/auth").permitAll()
                        .requestMatchers("/api/vocab/**").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.ALWAYS
                ))
                .logout(logout -> logout
                                .logoutSuccessUrl(appUrl)
                        .logoutUrl("/api/auth/logout")
                )

                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(login -> login.defaultSuccessUrl(appUrl + "/"))
                .build();
    }


    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(){
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request ->
        {
            OAuth2User oAuth2User = delegate.loadUser(request);
            AppUser appUser = userRepo.findById(oAuth2User.getName()).orElseGet(()-> {
                AppUser newAppUser = new AppUser(oAuth2User.getName(),
                        oAuth2User.getAttributes().get("login").toString(),
                        oAuth2User.getAttributes().get("avatar_url").toString(),
                "USER"
                );
                return userRepo.save(newAppUser);
            });

            return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(
                    appUser.authority())), oAuth2User.getAttributes(), "id");
        };

    }

}
