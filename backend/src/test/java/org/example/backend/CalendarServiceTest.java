package org.example.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalendarServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CalendarService calendarService = new CalendarService(mockVocabRepo);

//    @Disabled
//    @Test
//    void getVocabsOfMonth() {
//        // TODO hier müssten auch noch alle objekte erzeugt werden und dann matchen
//        //  sehr unübersichtlich und fehleranfalig -> methode stückeln? unklar
//        VocabIdsOfDate[][] expected = new VocabIdsOfDate[][]{
//                {
//                        null,
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 1), List.of("vocabId1", "vocabId2")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 2), List.of("vocabId3", "vocabId4")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 3), List.of("vocabId5", "vocabId6")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 4), List.of("vocabId7", "vocabId8")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 5), List.of("vocabId9", "vocabId10")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 6), List.of("vocabId11", "vocabId12"))
//                },
//                {
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 7), List.of("vocabId13", "vocabId14")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 8), List.of("vocabId15", "vocabId16")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 9), List.of("vocabId17", "vocabId18")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 10), List.of("vocabId19", "vocabId20")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 11), List.of("vocabId21", "vocabId22")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 12), List.of("vocabId23", "vocabId24")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 13), List.of("vocabId25", "vocabId26"))
//                },
//                {
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 14), List.of("vocabId27", "vocabId28")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 15), List.of("vocabId29", "vocabId30")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 16), List.of("vocabId31", "vocabId32")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 17), List.of("vocabId33", "vocabId34")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 18), List.of("vocabId35", "vocabId36")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 19), List.of("vocabId37", "vocabId38")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 20), List.of("vocabId39", "vocabId40"))
//                },
//                {
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 21), List.of("vocabId41", "vocabId42")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 22), List.of("vocabId43", "vocabId44")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 23), List.of("vocabId45", "vocabId46")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 24), List.of("vocabId47", "vocabId48")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 25), List.of("vocabId49", "vocabId50")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 26), List.of("vocabId51", "vocabId52")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 27), List.of("vocabId53", "vocabId54"))
//                },
//                {
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 28), List.of("vocabId55", "vocabId56")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 29), List.of("vocabId57", "vocabId58")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 30), List.of("vocabId59", "vocabId60")),
//                        new VocabIdsOfDate(LocalDate.of(2024, 10, 31), List.of("vocabId61", "vocabId62")),
//                        null,
//                        null,
//                        null
//                }
//
//
//        };
//
//        VocabIdsOfDate[][] actual = calendarService.getVocabsOfMonth(YearMonth.now());
//        assertEquals(expected, actual);
//    }


    @Test
    void getVocabIdsOfDate() {
        List<LocalDate> reviewDates = new ArrayList<>(List.of());
        LocalDate date = LocalDate.of(2024, 10, 18);
        reviewDates.add(date);
        List<LocalDate> otherDates = new ArrayList<>(List.of());
        otherDates.add(LocalDate.of(2024, 10, 16));
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