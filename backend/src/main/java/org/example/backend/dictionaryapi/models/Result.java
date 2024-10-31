package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Result(String id,
                     String language,
                     List<LexicalEntry> lexicalEntries,
                     String type,
                     String word) {
}

/*
   {
      "id": "string",
      "language": "string",
      "lexicalEntries": [
        null
      ],
      "type": "string",
      "word": "string",
      "pronunciations": [
        null
      ]
    }
 */