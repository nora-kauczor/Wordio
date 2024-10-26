package org.example.backend;

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
                "", Language.SPANISH.getValue(), List.of());
        vocabRepo.save(testVocab);
    }

    @Test
    void activateVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/activate/nonexistent-id"))
                .andExpect(status().isNotFound());
    }


    @Test
    void activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/activate/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.reviewDates").isNotEmpty());
    }


    @Test
    void getAllVocabs_ShouldReturnAllVocabs_whenCalled() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish", "reviewDates":[]}]
                        """));
    }


    @Test
    void getVocab_shouldReturnSpecificVocab_whenCalledWithItsId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish", "reviewDates":[]}
                        """));
    }


    @Test
    void getVocab_shouldReturn404_whenCalledWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/nonexistent-id"))
                .andExpect(status().isNotFound());
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
    void editVocab_shouldReturnEditedVocab_whenCalledWithThisVeryVocab() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"000","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "reviewDates":[]}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        { "_id":"000", "word":"la prueba", "translation":"test",
                                                                  "info":"added infotext", "language":"Spanish", "reviewDates":[]}
                        """));

    }


    @Test
    void editVocab_shouldReturn404_whenCalledWithVocabWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/vocab/nonexistent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "_id":"nonexistent-id","word":"la prueba", "translation":"test",
                                                                          "info":"added infotext", "language":"Spanish", "reviewDates":[]}
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