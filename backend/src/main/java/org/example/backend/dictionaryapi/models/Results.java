package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Results(
String id,
String language,
List<LexicalEntry> lexicalEntries,
String type,
String word
) {
}
