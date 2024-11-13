package org.example.backend.review;

import org.example.backend.vocab.Language;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Map;

public record ReviewDay(
        @Id  String id,
        LocalDate day,
        Language language,
        String userId,
        Map<String, Boolean> idsOfVocabsToReview) {
}
