package org.example.backend.vocab;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static org.example.backend.vocab.Vocab.generateDates;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VocabTest {

    @Test
    void generateDates_shouldReturnListOfDates_whenCalledWithDate() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(), "");
        testVocab.reviewDates = generateDates(LocalDate.now());
        LocalDate date1 = testVocab.reviewDates.get(0);
        LocalDate date2 = testVocab.reviewDates.get(1);
        LocalDate date5 = testVocab.reviewDates.get(4);
        assertEquals(date2, date1.plusDays(1));
        assertEquals(date5, date1.plusDays(10));
    }
}