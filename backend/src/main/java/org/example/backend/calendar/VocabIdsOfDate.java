package org.example.backend.calendar;



import java.time.LocalDate;
import java.util.List;


public record VocabIdsOfDate(
        LocalDate date,
        List<String> vocabIds
) {


}
