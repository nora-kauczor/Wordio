package org.example.backend.dictionaryapi.models;

public record CrossReference(String id, String text,
                             String type) {
}

/*
 "crossReferences": [
                    {
                      "id": "string",
                      "text": "string",
                      "type": "string"
                    }
                  ]
 */