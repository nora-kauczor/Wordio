package org.example.backend.vocab;


import java.time.LocalDate;
import java.util.List;

public record VocabDTOEdit(
        String _id,
        String word,
        String translation,
        String info,
        String language,
        List<LocalDate> reviewDates,
        boolean editable) {
}

