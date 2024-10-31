package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Result(
        String id,
        String language, // format: en / es
        List<LexicalEntry> lexicalEntries,
        String word
) {
}
