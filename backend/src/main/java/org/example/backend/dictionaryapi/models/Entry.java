package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Entry(
        List<Sense> senses
) {
}
