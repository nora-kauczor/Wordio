package org.example.backend.vocab;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.HashMap;

import static org.example.backend.vocab.Vocab.generateDates;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VocabTest {

    @Test
    void generateDates_shouldReturnListOfDates_whenCalledWithDate() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "");
        testVocab.getDatesPerUser().put("jane-doe", generateDates(LocalDate.now()));
        LocalDate date1 = testVocab.getDatesPerUser().get("jane-doe").get(0);
        LocalDate date2 = testVocab.getDatesPerUser().get("jane-doe").get(1);
        LocalDate date5 = testVocab.getDatesPerUser().get("jane-doe").get(4);
        assertEquals(date2, date1.plusDays(1));
        assertEquals(date5, date1.plusDays(10));
    }
}