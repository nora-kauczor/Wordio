package org.example.backend;

import java.time.LocalDate;
import java.util.List;

public record VocabDTO(
        String word,
        String translation,
        String info,
        String language,
        List<LocalDate> reviewDates) {
}

