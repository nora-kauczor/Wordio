package org.example.backend.check;

import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class CheckControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VocabRepo vocabRepo;

    @BeforeEach
    void setUp() {
        Vocab testVocab = new Vocab("vocab-id", "beau/belle", "beautiful",
                "", Language.FRENCH, new HashMap<>(), "Wordio");
        vocabRepo.save(testVocab);
    }

    @Test
    void isUserAnswerCorrect_ShouldReturnTrue_whenCalledWithIdOfBeauBelleAndUserAnswerBeau() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/check/vocab-id")
                        .param("answer", "beau")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        })))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void isUserAnswerCorrect_ShouldReturnFalse_whenCalledWithIdOfBeauBelleAndUserAnswerBaeu()throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/check/vocab-id")
                        .param("answer", "baeu")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        })))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}