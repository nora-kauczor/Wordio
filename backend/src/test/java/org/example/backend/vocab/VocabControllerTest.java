package org.example.backend.vocab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class VocabControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VocabRepo vocabRepo;


    @BeforeEach
    void setUp() {
        Map<String, List<LocalDate>> usersReviewDatesForVocab000 = new HashMap<>();
        usersReviewDatesForVocab000.put("jane-doe", List.of(LocalDate.of(2024, 11, 1)));
        Vocab vocabByWordio = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, usersReviewDatesForVocab000, "Wordio");
        Vocab vocabByUser = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "jane-doe");
        Vocab vocabDifferentLanguage = new Vocab("222", "la prueba", "test",
                "", Language.ITALIAN, new HashMap<>(), "Wordio");
        Vocab vocabByOtherUser = new Vocab("333", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "average-joe");
        vocabRepo.save(vocabByWordio);
        vocabRepo.save(vocabByUser);
        vocabRepo.save(vocabDifferentLanguage);
        vocabRepo.save(vocabByOtherUser);
    }


    @Test
    void changeReviewDates_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/change-dates/nonexistent-id")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("name", "jane-doe");
                        })))
                .andExpect(status().isNotFound());
    }


    @Test
    void changeReviewDates_shouldReturnVocabWithNewReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/change-dates/000")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.datesPerUser.jane-doe[0]").value("2024-11-02"));
    }

    @Test
    void deactivateVocab_shouldReturn404_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/deactivate/nonexistent-id")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isNotFound());
    }

    @Test
    void deactivateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/deactivate/000")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.datesPerUser.jane-doe").isEmpty());
    }


    @Test
    void activateVocab_shouldReturn404_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/activate/nonexistent-id")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isNotFound());
    }


    @Test
    void activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/activate/000")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.datesPerUser.jane-doe").isNotEmpty());
    }


    @Test
    void getAllVocabsOfLanguage_shouldReturn404_whenCalledWithNonexistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab?language=Esperanto")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllVocabsOfLanguage_ShouldReturnAllVocabsOfLanguage_whenCalledWithExistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab?language=Spanish")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"},
                  {"_id":"111", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}]
                  """))
                .andExpect(jsonPath("$[0].datesPerUser.jane-doe[0]").value("2024-11-01"));
    }


    @Test
    void createVocab_shouldReturnNewVocabObject_whenCalledWithVocabDTO() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/vocab")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "word":"la prueba", "translation":"test",
                                                                          "info":"", "language":"Spanish", "createdBy":  "jane-doe"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "word":"la prueba", "translation":"test",
                                                                  "info":"", "language":"Spanish", "datesPerUser":{}, "createdBy":  "jane-doe"}
                        """))
                .andExpect(jsonPath("$._id").isNotEmpty());

    }


    @Test
    void createVocab_shouldReturn404_whenCalledWithVocabWithNonexistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/vocab")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "word":"la prueba", "translation":"test",
                                                                          "info":"", "language":"Esperanto","createdBy":  "maxi-muster"}
                                """))
                .andExpect(status().isNotFound());
    }


    @Test
    void createAndActivateVocab_shouldReturnNewVocabObjectWithReviewDates_whenCalledWithVocabDTO() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/vocab/activate")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "word":"la prueba", "translation":"test",
                                                                          "info":"", "language":"Spanish", "createdBy":  "jane-doe"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "word":"la prueba", "translation":"test",
                                                                  "info":"", "language":"Spanish", "createdBy":  "jane-doe"}
                        """))
                .andExpect(jsonPath("$._id").isNotEmpty())
                .andExpect(jsonPath("$.datesPerUser.jane-doe").isNotEmpty());

    }


    @Test
    void createAndActivateVocab_shouldReturn404_whenCalledWithVocabWithNonExistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/vocab/activate")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "word":"la prueba", "translation":"test",
                                                                          "info":"", "language":"Esperanto","createdBy":  "maxi-muster"}
                                """))
                .andExpect(status().isNotFound());
    }


    @Test
    void editVocab_shouldReturnEditedVocab_whenCalledWithVocabDTOEdit() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"111","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "datesPerUser":{}, "createdBy":  "jane-doe"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "_id":"111", "word":"la prueba", "translation":"test",
                                                                  "info":"added infotext", "language":"Spanish", "datesPerUser":{}, "createdBy":  "jane-doe"}
                        """));

    }


    @Test
    void editVocab_shouldReturn404_whenCalledWithVocabWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"nonexistent-id","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "datesPerUser":{}, "createdBy":  "Wordio"}
                                """))
                .andExpect(status().isNotFound());
    }


    @Test
    void editVocab_shouldReturn405_whenCalledWithNonEditableVocab() throws Exception {
        Vocab nonEditableVocab = new Vocab("123", "la prueba", "test", "", Language.SPANISH, new HashMap<>(), "Wordio");
        vocabRepo.save(nonEditableVocab);
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("sub", "jane-doe");
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"123","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "datesPerUser":{}, "createdBy":  "Wordio"}
                                """))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteVocab_shouldReturnString_whenCalledWithIdOfEditableVocab() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/vocab/111")
                        .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isOk())
                .andExpect(content().string("Vocab successfully deleted."));
    }


    @Test
    void deleteVocab_shouldReturn404_whenCalledWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/vocab/nonexistent-id")
                .with(oauth2Login().attributes(attributes -> {
                    attributes.put("sub", "jane-doe");
                })))
                .andExpect(status().isNotFound());
    }


}