package org.example.backend.vocab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
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
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(LocalDate.of(2024, 11, 1)), "maxi-muster");
        vocabRepo.save(testVocab);
    }

    @Test
    void changeReviewDates_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/change-dates/nonexistent-id"))
                .andExpect(status().isNotFound());
    }


    @Test
    void changeReviewDates_shouldReturnVocabWithNewReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/change-dates/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.reviewDates[0]").value("2024-11-02"));
    }

    @Test
    void deactivateVocab_shouldReturn404_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/deactivate/nonexistent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deactivateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/deactivate/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.reviewDates").isEmpty());
    }

    @Test
    void activateVocab_shouldReturn404_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/activate/nonexistent-id"))
                .andExpect(status().isNotFound());
    }


    @Test
    void activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/activate/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.reviewDates").isNotEmpty());
    }

    @Test
    void getAllVocabsOfLanguage_shouldReturn404_whenCalledWithNonexistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab?language=Esperanto"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllVocabsOfLanguage_ShouldReturnAllVocabsOfLanguage_whenCalledWithExistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab?language=Spanish"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}]
                        """))
                .andExpect(jsonPath("$[0].reviewDates[0]").value("2024-11-01"));
    }



    @Test
    void createVocab_shouldReturnNewVocabObject_whenCalledWithVocabDTO() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/vocab")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "word":"la prueba", "translation":"test",
                                                                          "info":"", "language":"Spanish", "reviewDates":[]}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "word":"la prueba", "translation":"test",
                                                                  "info":"", "language":"Spanish", "reviewDates":[]}
                        """))
                .andExpect(jsonPath("$._id").isNotEmpty());

    }

    @Test
    void createVocab_shouldReturn404_whenCalledWithVocabWithNonexistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/vocab")
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"000","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "reviewDates":[], "createdBy":  "maxi-muster"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "_id":"000", "word":"la prueba", "translation":"test",
                                                                  "info":"added infotext", "language":"Spanish", "reviewDates":[], "createdBy":  "maxi-muster"}
                        """));

    }


    @Test
    void editVocab_shouldReturn404_whenCalledWithVocabWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"nonexistent-id","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "reviewDates":[], "createdBy":  "Wordio"}
                                """))
                .andExpect(status().isNotFound());
    }


    @Test
    void editVocab_shouldReturn405_whenCalledWithNonEditableVocab() throws Exception {
        Vocab nonEditableVocab = new Vocab("123", "la prueba", "test", "", Language.SPANISH, List.of(), "Wordio");
        vocabRepo.save(nonEditableVocab);
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"123","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "reviewDates":[], "createdBy":  "Wordio"}
                                """))
        .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void editVocab_shouldReturn404_whenCalledWithVocabWithNonexistentLanguage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"000","word":"la prueba", "translation":"test",
                                                                          "info":"", "language":"Esperanto", "reviewDates":[], "createdBy":  "maxi-muster"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVocab_shouldReturnString_whenCalledWithId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/vocab/000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Vocab successfully deleted."));
    }

    @Test
    void deleteVocab_shouldReturn404_whenCalledWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/vocab/nonexistent-id"))
                .andExpect(status().isNotFound());
    }


}