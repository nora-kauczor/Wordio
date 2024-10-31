package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Sense(List<DatasetCrossLink> datasetCrossLinks,
                    List<String> definitions,
                    List<String> shortDefinitions,
                    String id,
                    List<Subsense> subsenses,
                    List<Translation> translations,
                    List<String> crossReferenceMarkers,
                    List<CrossReference> crossReferences) {

}
