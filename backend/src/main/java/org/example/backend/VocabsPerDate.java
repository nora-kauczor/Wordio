package org.example.backend;


import java.time.LocalDate;
import java.util.List;


public record VocabsPerDate(
        String date,
        List<String> vocabIds
) {


}
