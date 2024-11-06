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


    @GetMapping("/name")
    public AppUser getUserName(@AuthenticationPrincipal OAuth2User user) {
        if
       ( user == null) {
            return new AppUser("NotFound", "", null, null);
        }
        return userRepo.findById(user.getName()).orElseThrow();
    }

    @GetMapping
    public String getUserId(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
