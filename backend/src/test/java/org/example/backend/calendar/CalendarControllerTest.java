package org.example.backend.calendar;

import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    void getMonth_shouldReturnMonth_whenCalledWithYearMonthNow() throws Exception {
        LocalDate date011024 = LocalDate.of(2024, 10, 1);
        LocalDate date021024 = LocalDate.of(2024, 10, 2);
        LocalDate date031024 = LocalDate.of(2024, 10, 3);
        LocalDate date161024 = LocalDate.of(2024, 10, 16);
        LocalDate date181024 = LocalDate.of(2024, 10, 18);
        Map<String, List<LocalDate>> datesPerUser1 = new HashMap<>();
        datesPerUser1.put("jane-doe", List.of(date011024, date021024, date031024));
        Vocab testVocab1 = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser1, "");
        Map<String, List<LocalDate>> datesPerUser2 = new HashMap<>();
        datesPerUser2.put("jane-doe", List.of(date161024, date181024));
        Vocab testVocab2 = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH, datesPerUser2, "");
        Map<String, List<LocalDate>> datesPerUser3 = new HashMap<>();
        datesPerUser3.put("jane-doe", List.of(date181024));
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.SPANISH, datesPerUser3, "");
        Vocab vocabDifferentLanguage = new Vocab("333", "la prueba", "test",
                "", Language.ITALIAN, datesPerUser3, "");
        vocabRepo.save(testVocab1);
        vocabRepo.save(testVocab2);
        vocabRepo.save(testVocab3);
        vocabRepo.save(vocabDifferentLanguage);
        mvc.perform(MockMvcRequestBuilders.get("/api/calendar?year=2024&month=10&language=Spanish&user=jane-doe"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"yearMonthName": "October 2024", "vocabIdsOfMonth":
                        [
                            [
                                null,
                                {"date": "2024-10-01", "vocabIds": ["000"]},
                                {"date": "2024-10-02", "vocabIds": ["000"]},
                                {"date": "2024-10-03", "vocabIds": ["000"]},
                                {"date": "2024-10-04", "vocabIds": []},
                                {"date": "2024-10-05", "vocabIds": []},
                                {"date": "2024-10-06", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-07", "vocabIds": []},
                                {"date": "2024-10-08", "vocabIds": []},
                                {"date": "2024-10-09", "vocabIds": []},
                                {"date": "2024-10-10", "vocabIds": []},
                                {"date": "2024-10-11", "vocabIds": []},
                                {"date": "2024-10-12", "vocabIds": []},
                                {"date": "2024-10-13", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-14", "vocabIds": []},
                                {"date": "2024-10-15", "vocabIds": []},
                                {"date": "2024-10-16", "vocabIds": ["111"]},
                                {"date": "2024-10-17", "vocabIds": []},
                                {"date": "2024-10-18", "vocabIds": ["111", "222"]},
                                {"date": "2024-10-19", "vocabIds": []},
                                {"date": "2024-10-20", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-21", "vocabIds": []},
                                {"date": "2024-10-22", "vocabIds": []},
                                {"date": "2024-10-23", "vocabIds": []},
                                {"date": "2024-10-24", "vocabIds": []},
                                {"date": "2024-10-25", "vocabIds": []},
                                {"date": "2024-10-26", "vocabIds": []},
                                {"date": "2024-10-27", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-28", "vocabIds": []},
                                {"date": "2024-10-29", "vocabIds": []},
                                {"date": "2024-10-30", "vocabIds": []},
                                null,
                                null,
                                null,
                                null
                            ]
                        ]
                        }
                        """));
    }

    @Test
    void getMonth_shouldReturnMonth_whenCalledWithYearMonthNow_noVocabularyScheduled() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/calendar?year=2024&month=10&language=Spanish&user=jane-doe"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"yearMonthName": "October 2024", "vocabIdsOfMonth":
                        [
                            [
                                null,
                                {"date": "2024-10-01", "vocabIds": []},
                                {"date": "2024-10-02", "vocabIds": []},
                                {"date": "2024-10-03", "vocabIds": []},
                                {"date": "2024-10-04", "vocabIds": []},
                                {"date": "2024-10-05", "vocabIds": []},
                                {"date": "2024-10-06", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-07", "vocabIds": []},
                                {"date": "2024-10-08", "vocabIds": []},
                                {"date": "2024-10-09", "vocabIds": []},
                                {"date": "2024-10-10", "vocabIds": []},
                                {"date": "2024-10-11", "vocabIds": []},
                                {"date": "2024-10-12", "vocabIds": []},
                                {"date": "2024-10-13", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-14", "vocabIds": []},
                                {"date": "2024-10-15", "vocabIds": []},
                                {"date": "2024-10-16", "vocabIds": []},
                                {"date": "2024-10-17", "vocabIds": []},
                                {"date": "2024-10-18", "vocabIds": []},
                                {"date": "2024-10-19", "vocabIds": []},
                                {"date": "2024-10-20", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-21", "vocabIds": []},
                                {"date": "2024-10-22", "vocabIds": []},
                                {"date": "2024-10-23", "vocabIds": []},
                                {"date": "2024-10-24", "vocabIds": []},
                                {"date": "2024-10-25", "vocabIds": []},
                                {"date": "2024-10-26", "vocabIds": []},
                                {"date": "2024-10-27", "vocabIds": []}
                            ],
                            [
                                {"date": "2024-10-28", "vocabIds": []},
                                {"date": "2024-10-29", "vocabIds": []},
                                {"date": "2024-10-30", "vocabIds": []},
                                null,
                                null,
                                null,
                                null
                            ]
                        ]
                        }
                        """));
    }

    @Test
    void getMonth_ShouldReturn404_whenCalledWithNonExistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/calendar?year=2024&month=10&language=Esperanto&user=jane-doe"))
                .andExpect(status().isNotFound());
    }

}