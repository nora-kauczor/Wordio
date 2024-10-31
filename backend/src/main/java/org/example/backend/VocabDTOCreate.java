package org.example.backend;

public record VocabDTOCreate(
        String word,
        String translation,
        String info,
        String language,
        boolean editable) {
}
