package org.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.not;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class CalendarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VocabRepo vocabRepo;


    @Test
    void getVocabsOfMonth_shouldReturnArrayOfCurrentMonth_whenCalledWithYearMonthNow() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of(LocalDate.now()));
        vocabRepo.save(testVocab);
        mvc.perform(MockMvcRequestBuilders.get("/api/calendar/current-month"))
                .andExpect(status().isOk());
    }

    @Test
    void getVocabsOfMonth2_shouldReturnArrayOfCertainMonth_whenCalledWithAYearMonth() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of(LocalDate.of(2024, 11, 11)));
        vocabRepo.save(testVocab);
        mvc.perform(MockMvcRequestBuilders.get("/api/calendar/other-month?year=2024&month=11"))
                .andExpect(status().isOk());
    }
}