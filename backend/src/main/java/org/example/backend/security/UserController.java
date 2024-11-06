package org.example.backend.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vocab/auth")
public class UserController {

    private final UserRepo userRepo;

    @GetMapping
    public String getUserId(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
