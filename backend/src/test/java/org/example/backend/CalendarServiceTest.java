package org.example.backend;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalendarServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CalendarService calendarService = new CalendarService(mockVocabRepo);

    @Test
    void getVocabIdsOfDate() {
        List<String> reviewDates = new ArrayList<>(List.of());
        String date = "Fri-18-10-2024";
        reviewDates.add(date);
        List<String> otherDates = new ArrayList<>(List.of());
        otherDates.add("Wed-16-10-2024");
        Vocab testVocab1 = new Vocab("000", "la prueba", "test",
                "", "Spanish", reviewDates);
        Vocab testVocab2 = new Vocab("777", "la prueba", "test",
                "", "Spanish", reviewDates);
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", "Spanish", otherDates);
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab1, testVocab2, testVocab3));
        VocabIdsOfDate expected = new VocabIdsOfDate(date, List.of("000", "777"));
        VocabIdsOfDate actual = calendarService.getVocabIdsOfDate(date);
        assertEquals(expected, actual);
    }
}