package org.example.backend;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class VocabTest {

    @Test
    void generateDates_shouldReturnListOfDates_whenCalledWithDate() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", Collections.emptyList());
        testVocab.reviewDates = testVocab.generateDates(LocalDate.now());
        LocalDate date1 = testVocab.reviewDates.get(0);
        LocalDate date2 = testVocab.reviewDates.get(1);
        LocalDate date5 = testVocab.reviewDates.get(4);
        assertEquals(date2, date1.plusDays(1));
        assertEquals(date5, date1.plusDays(10));
    }
}