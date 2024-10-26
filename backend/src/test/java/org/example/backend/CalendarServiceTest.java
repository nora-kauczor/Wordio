package org.example.backend;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalendarServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CalendarService calendarService = new CalendarService(mockVocabRepo);


    @Test
    void getVocabsOfMonth() {
        LocalDate date011024 = LocalDate.of(2024, 10, 1);
        LocalDate date021024 = LocalDate.of(2024, 10, 2);
        LocalDate date031024 = LocalDate.of(2024, 10, 3);
        LocalDate date161024 = LocalDate.of(2024, 10, 16);
        LocalDate date181024 = LocalDate.of(2024, 10, 18);
        Vocab testVocab1 = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH.getValue(), List.of(date011024, date021024, date031024));
        Vocab testVocab2 = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH.getValue(), List.of(date161024, date181024));
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.SPANISH.getValue(), List.of(date181024));
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab1, testVocab2, testVocab3));
        VocabIdsOfDate[][] expected = new VocabIdsOfDate[][]{
                {
                        null,
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 1), List.of("000")),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 2), List.of("000")),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 3), List.of("000")),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 4), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 5), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 6), List.of())
                },
                {
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 7), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 8), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 9), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 10), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 11), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 12), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 13), List.of())
                },
                {
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 14), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 15), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 16), List.of("111")),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 17), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 18), List.of("111", "222")),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 19), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 20), List.of())
                },
                {
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 21), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 22), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 23), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 24), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 25), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 26), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 27), List.of())
                },
                {
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 28), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 29), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 30), List.of()),
                        new VocabIdsOfDate(LocalDate.of(2024, 10, 31), List.of()),
                        null,
                        null,
                        null
                }


        };
        VocabIdsOfDate[][] actual = calendarService.getVocabsOfMonth(YearMonth.of(2024, 10));
        assertArrayEquals(expected[0], actual[0]);
    }


    @Test
    void getVocabIdsOfDate() {
        List<LocalDate> reviewDates = new ArrayList<>(List.of());
        LocalDate date = LocalDate.of(2024, 10, 18);
        reviewDates.add(date);
        List<LocalDate> otherDates = new ArrayList<>(List.of());
        otherDates.add(LocalDate.of(2024, 10, 16));
        Vocab testVocab1 = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH.getValue(), reviewDates);
        Vocab testVocab2 = new Vocab("777", "la prueba", "test",
                "", Language.SPANISH.getValue(), reviewDates);
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.SPANISH.getValue(), otherDates);
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab1, testVocab2, testVocab3));
        VocabIdsOfDate expected = new VocabIdsOfDate(date, List.of("000", "777"));
        VocabIdsOfDate actual = calendarService.getVocabIdsOfDate(date);
        assertEquals(expected, actual);
    }


}