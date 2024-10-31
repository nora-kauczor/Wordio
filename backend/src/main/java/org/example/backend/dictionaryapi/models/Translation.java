package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Translation(
        List<GrammaticalFeature> grammaticalFeatures,
        String language, // Zielsprache, z.B. "es"
        String text // word (Übersetzung)
) {
}