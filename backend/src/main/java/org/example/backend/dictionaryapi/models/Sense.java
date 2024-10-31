package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Sense(
        List<Translation> translations
) {
}
