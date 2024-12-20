package org.example.backend.calendar;

import org.example.backend.vocab.Language;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalendarServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CalendarService calendarService = new CalendarService(mockVocabRepo);


    @Test
    void getMonth_ShouldReturnMonthObjectWithYearMonthName_whenCalledWithYearMonth() throws LanguageNotFoundException {
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
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab1, testVocab2, testVocab3, vocabDifferentLanguage));
        String expectedYearMonthName = "October 2024";
        VocabIdsOfDate[][] expectedVocabIdsOfMonth = new VocabIdsOfDate[5][7];
        Month expected = new Month(expectedYearMonthName, expectedVocabIdsOfMonth);
        Month actual = calendarService.getMonth(YearMonth.of(2024, 10), "Spanish", "jane-doe");
        assertEquals(expected.yearMonthName(), actual.yearMonthName());
    }

    @Test
    void getVocabsOfMonth_ShouldReturnMonthObjectWith2DWithDatesOfYearMonth_whenCalledWithYearMonth() throws LanguageNotFoundException {
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
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab1, testVocab2, testVocab3, vocabDifferentLanguage));
        String expectedYearMonthName = "October 2024";
        VocabIdsOfDate[][] expectedVocabIdsOfMonth = new VocabIdsOfDate[][]{
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

        Month expected = new Month(expectedYearMonthName, expectedVocabIdsOfMonth);
        Month actual = calendarService.getMonth(YearMonth.of(2024, 10), "Spanish", "jane-doe");
        assertArrayEquals(expected.vocabIdsOfMonth()[0], actual.vocabIdsOfMonth()[0]);
    }


    @Test
    void getMonth_ShouldThrowLanguageNotFoundException_whenCalledWithNonExistentLanguage()  {
        assertThrows(LanguageNotFoundException.class, () ->
                calendarService.getMonth(YearMonth.of(2024, 12), "Esperanto", "jane-doe"));
    }


    @Test
    void getVocabIdsOfDate_ShouldReturnIdsOFAllVocabsOfALanguageThatAreToBeReviewedAtCertainDate_whenCalledWithDateAndLanguage() {
        LocalDate date = LocalDate.of(2024, 10, 18);
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of(date));
        Map<String, List<LocalDate>> otherDatesPerUser = new HashMap<>();
        otherDatesPerUser.put("jane-doe", List.of(LocalDate.of(2024, 10, 16)));
        Vocab testVocab1 = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "");
        Vocab testVocab2 = new Vocab("777", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "");
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.SPANISH, otherDatesPerUser, "");
        Vocab vocabDifferentLanguage = new Vocab("333", "la prueba", "test",
                "", Language.ITALIAN, datesPerUser, "");
        Map<String, List<LocalDate>> datesPerUserWithoutRelevantUser = new HashMap<>();
        datesPerUserWithoutRelevantUser.put("robin", List.of(date));
        Vocab vocabNotActiveForUser = new Vocab("222", "la prueba", "test",
                "", Language.SPANISH, datesPerUserWithoutRelevantUser, "");
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab1, testVocab2, testVocab3, vocabDifferentLanguage, vocabNotActiveForUser));
        VocabIdsOfDate expected = new VocabIdsOfDate(date, List.of("000", "777"));
        VocabIdsOfDate actual = calendarService.getVocabIdsOfDate(date, Language.SPANISH, "jane-doe");
        assertEquals(expected, actual);
    }


}