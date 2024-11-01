package org.example.backend.vocab;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record VocabDTOEdit(
        String _id,
        String word,
        String translation,
        String info,
        String language,
         Map<String, List<LocalDate>> datesPerUser,
        String createdBy) {
}



