package org.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class VocabControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VocabRepo vocabRepo;

    @DirtiesContext
    @Test
    void activateVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/activate/000"))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
    @Test
    void  activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", Collections.emptyList());
        vocabRepo.save(testVocab);
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/activate/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"}
                        """))
                .andExpect(jsonPath("$.reviewDates").isNotEmpty());
    }

    @DirtiesContext
    @Test
    void getTodaysVocabs_shouldReturnTodaysVocabs_whenCalled() throws Exception {
        LocalDate today = LocalDate.now();
        List<LocalDate> datesContainingToday = new ArrayList<>(List.of(today));
        Vocab vocab1 = new Vocab("111", "la prueba", "test",
                "", "Spanish", datesContainingToday);
        Vocab vocab2 = new Vocab("222", "el libro", "book",
                "", "Spanish", datesContainingToday);
        Vocab vocab3 = new Vocab("333", "la casa", "house",
                "", "Spanish", List.of());
        vocabRepo.save(vocab1);
        vocabRepo.save(vocab2);
        vocabRepo.save(vocab3);
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/today"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"_id":"111", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish"},
                  {"_id":"222", "word":"el libro", "translation":"book",
                                          "info":"", "language":"Spanish"}
]
                        """))
                .andExpect(jsonPath("$[0].reviewDates").isNotEmpty())
                .andExpect(jsonPath("$[1].reviewDates").isNotEmpty());
    }


    @DirtiesContext
    @Test
    void getTodaysVocabs_throws204_whenCalledOnADayWithoutAnyVocabs() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/today"))
                .andExpect(status().isNoContent());
    }


    @DirtiesContext
    @Test
    void getAllVocabs_ShouldReturnAllVocabs_whenCalled() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of());
        vocabRepo.save(testVocab);
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish", "reviewDates":[]}]
                        """));
    }


    @DirtiesContext
    @Test
    void getVocab_shouldReturnSpecificVocab_whenCalledWithItsId() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of());
        vocabRepo.save(testVocab);
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/000"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_id":"000", "word":"la prueba", "translation":"test",
                                          "info":"", "language":"Spanish", "reviewDates":[]}
                        """));
    }

    @DirtiesContext
    @Test
    void getVocab_shouldReturn404_whenCalledWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/vocab/000"))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
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



    @DirtiesContext
    @Test
    void editVocab_shouldReturnEditedVocab_whenCalledWithThisVeryVocab() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of());
        vocabRepo.save(testVocab);
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

    @DirtiesContext
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

    @DirtiesContext
    @Test
    void deleteVocab_shouldReturnString_whenCalledWithId() throws Exception {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of());
        vocabRepo.save(testVocab);
        mvc.perform(MockMvcRequestBuilders.delete("/api/vocab/000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Vocab successfully deleted."));
    }

    @DirtiesContext
    @Test
    void deleteVocab_shouldReturn404_whenCalledWithNonexistentID() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/vocab/000"))
                .andExpect(status().isNotFound());
    }


}