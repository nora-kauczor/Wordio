package org.example.backend.review;

import org.example.backend.vocab.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewDayControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReviewDayRepo reviewDayRepo;

    @BeforeEach
    void setUp() {
        LocalDate date = ZonedDateTime.now().toLocalDate();
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put("vocab-id", false);
        ReviewDay reviewDay = new ReviewDay("000", date, Language.SPANISH, "user id", idsOfVocabsToReview);
        reviewDayRepo.save(reviewDay);
    }


    @Test
    void getReviewDay() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/review?language=Spanish")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "user id");
                        })))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "id": "000",
                                  "userId":  "user id",
                                 "idsOfVocabsToReview": {"vocab-id":  false} }
                       """))
                .andExpect(jsonPath("$.day").value(ZonedDateTime.now().toLocalDate().toString()));
    }

    @Test
    void setVocabReviewed() throws Exception {
                mvc.perform(MockMvcRequestBuilders.put("/api/review/vocab-id")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "user id");
                        })))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "id": "000",
                                  "userId":  "user id",
                                 "idsOfVocabsToReview": {"vocab-id":  true} }
                       """))
                        .andExpect(jsonPath("$.day").value(ZonedDateTime.now().toLocalDate().toString()));
    }
}