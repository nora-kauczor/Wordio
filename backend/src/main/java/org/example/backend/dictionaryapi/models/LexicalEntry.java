package org.example.backend.dictionaryapi.models;

import java.util.List;

public record LexicalEntry(
        List<Entry> entries,
        String language,
        String text,
        String root,
        LexicalCategory lexicalCategory

) {
}


