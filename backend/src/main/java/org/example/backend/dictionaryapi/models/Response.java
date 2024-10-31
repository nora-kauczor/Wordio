package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Response(List<Result> results){
}

/*
{
  "metadata": {},
  "results": [
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
  ]
}
 */