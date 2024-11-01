package org.example.backend.vocab;

public record VocabDTOCreate(
        String word,
        String translation,
        String info,
        String language,
        String createdBy) {
}
