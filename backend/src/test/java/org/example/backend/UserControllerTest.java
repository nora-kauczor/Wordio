package org.example.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;


    @DirtiesContext
    @Test
    void getUser_shouldReturn200AndUserObject_ifUserIsLoggedIn_whenCalled() throws Exception {
        AppUser testUser = new AppUser("user", "Hans", null, null);
        userRepo.save(testUser);
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/auth")
                        .with(oidcLogin()
                                .userInfoToken(token -> token.claim("login", "Hans"))))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"id":"user","name":"Hans","avatarUrl":null,"authority":null}
                        """));

    }

    @Disabled
    @DirtiesContext
    @Test
    void getUser_shouldReturn302AndDummyUserObject_ifUserIsLoggedIn_whenCalled() throws Exception {
        AppUser testUser = new AppUser("user", "Hans", null, null);
        userRepo.save(testUser);
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/auth"))
                .andExpect(status().isFound())
                .andExpect(content().json("""
                        {"id":"NotFound","name":"anonymousUser","avatarUrl":null,"authority":null}
                        """))
        ;

    }
}