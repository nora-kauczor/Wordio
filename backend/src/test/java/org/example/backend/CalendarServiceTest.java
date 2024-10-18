package org.example.backend;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalendarServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CalendarService calendarService = new CalendarService(mockVocabRepo);

    @Test
    void getVocabsOfMonth() {
        // TODO hier müssten auch noch alle objekte erzeugt werden und dann matchen
        //  sehr unübersichtlich und fehleranfalig -> methode stückeln? unklar
        VocabIdsOfDate[][] expected = new VocabIdsOfDate[][] {
                {
                        null,
                        new VocabIdsOfDate("2024-10-01", List.of("vocabId1", "vocabId2")),
                        new VocabIdsOfDate("2024-10-02", List.of("vocabId3", "vocabId4")),
                        new VocabIdsOfDate("2024-10-03", List.of("vocabId5", "vocabId6")),
                        new VocabIdsOfDate("2024-10-04", List.of("vocabId7", "vocabId8")),
                        new VocabIdsOfDate("2024-10-05", List.of("vocabId9", "vocabId10")),
                        new VocabIdsOfDate("2024-10-06", List.of("vocabId11", "vocabId12"))
                },
                {
                        new VocabIdsOfDate("2024-10-07", List.of("vocabId13", "vocabId14")),
                        new VocabIdsOfDate("2024-10-08", List.of("vocabId15", "vocabId16")),
                        new VocabIdsOfDate("2024-10-09", List.of("vocabId17", "vocabId18")),
                        new VocabIdsOfDate("2024-10-10", List.of("vocabId19", "vocabId20")),
                        new VocabIdsOfDate("2024-10-11", List.of("vocabId21", "vocabId22")),
                        new VocabIdsOfDate("2024-10-12", List.of("vocabId23", "vocabId24")),
                        new VocabIdsOfDate("2024-10-13", List.of("vocabId25", "vocabId26"))
                },
                {
                        new VocabIdsOfDate("2024-10-14", List.of("vocabId27", "vocabId28")),
                        new VocabIdsOfDate("2024-10-15", List.of("vocabId29", "vocabId30")),
                        new VocabIdsOfDate("2024-10-16", List.of("vocabId31", "vocabId32")),
                        new VocabIdsOfDate("2024-10-17", List.of("vocabId33", "vocabId34")),
                        new VocabIdsOfDate("2024-10-18", List.of("vocabId35", "vocabId36")),
                        new VocabIdsOfDate("2024-10-19", List.of("vocabId37", "vocabId38")),
                        new VocabIdsOfDate("2024-10-20", List.of("vocabId39", "vocabId40"))
                },
                {
                        new VocabIdsOfDate("2024-10-21", List.of("vocabId41", "vocabId42")),
                        new VocabIdsOfDate("2024-10-22", List.of("vocabId43", "vocabId44")),
                        new VocabIdsOfDate("2024-10-23", List.of("vocabId45", "vocabId46")),
                        new VocabIdsOfDate("2024-10-24", List.of("vocabId47", "vocabId48")),
                        new VocabIdsOfDate("2024-10-25", List.of("vocabId49", "vocabId50")),
                        new VocabIdsOfDate("2024-10-26", List.of("vocabId51", "vocabId52")),
                        new VocabIdsOfDate("2024-10-27", List.of("vocabId53", "vocabId54"))
                },
                {
                        new VocabIdsOfDate("2024-10-28", List.of("vocabId55", "vocabId56")),
                        new VocabIdsOfDate("2024-10-29", List.of("vocabId57", "vocabId58")),
                        new VocabIdsOfDate("2024-10-30", List.of("vocabId59", "vocabId60")),
                        new VocabIdsOfDate("2024-10-31", List.of("vocabId61", "vocabId62")),
                        null,
                        null,
                        null
                }
        };

        VocabIdsOfDate[][] actual = calendarService.getVocabsOfMonth();
        assertEquals(expected, actual);
    }


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