package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Subsense(
        List<DatasetCrossLink> datasetCrossLinks,
        List<String> definitions,
        List<String> shortDefinitions,
        String id,
        List<Subsense> subsenses,
        List<Translation> translations) {
}


