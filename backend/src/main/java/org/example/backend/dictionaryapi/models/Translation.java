package org.example.backend.dictionaryapi.models;

public record Translation(String text, String language, String type) {
}

/*
{
                          "text": "string",
                          "language": "string",
                          "toneGroups": [
                            {
                              "tones": []
                            }
                          ],
                          "type": "string"
                        }
                        */