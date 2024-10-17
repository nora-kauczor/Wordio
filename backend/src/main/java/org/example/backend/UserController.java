package org.example.backend;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public AppUser getUser(@AuthenticationPrincipal OAuth2User user) {
        if
       ( user == null) {
            return new AppUser("not found", "anonymousUser", null, null);
        }
               return userRepo.findById(user.getName()).orElseThrow();
    }

}