package org.example.backend;


public record VocabDTO(
        String word,
        String translation,
        String info,
        String language) {
}

